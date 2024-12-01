package org.example.be.post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.be.post.dto.GetPageResponse;
import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.example.be.user.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final ImageRepository imageRepository;
	private final PostLikeRepository postLikeRepository;

	public void writePost(WritePostRequest request, List<MultipartFile> imageFile, Member member) throws IOException {

		String imagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.owner(member)
			.build();

		List<PostImageRelation> relations = new ArrayList<>();

		if (imageFile != null) {
			for (MultipartFile imageFiles : imageFile) {
				if (imageFiles != null && !imageFiles.isEmpty()) {
					String fileName = imageFiles.getOriginalFilename();
					String newFileName = UUID.randomUUID() + "_" + fileName;
					File filePath = new File(imagePath, newFileName);
					imageFiles.transferTo(filePath);

					Image image = Image.builder()
						.imageName(newFileName)
						.imagePath(imagePath + File.separator + newFileName)
						.build();

					PostImageRelation relation = new PostImageRelation();
					relation.setPost(post);
					relation.setImage(image);

					relations.add(relation);
					imageRepository.save(image);
				}
			}
		}

		postRepository.save(post);
		post.setPostImageRelations(relations);
	}

	@Transactional(readOnly = true)
	public GetPostResponse readPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		List<String> imagePaths = post.getPostImageRelations().stream()
			.map(relation -> relation.getImage().getImagePath())
			.toList();

		GetPostResponse.PostDetail postDetail = new GetPostResponse.PostDetail(
			post.getId(), post.getTitle(), post.getContent(), imagePaths
		);

		return new GetPostResponse(postDetail);
	}

	public GetPageResponse readPage(int pageNumber, int pageSize, String criteria) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, criteria));

		Page<Post> postPage = postRepository.findAll(pageable);

		List<GetPageResponse.PostDetail> postDetails = postPage.getContent().stream()
			.map(post -> new GetPageResponse.PostDetail(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				post.getPostImageRelations()
					.stream()
					.findFirst()
					.map(relation -> relation.getImage().getImagePath())
					.orElse(null)
			))
			.toList();

		return new GetPageResponse(postDetails, postPage.getNumber(), postPage.getTotalPages());
	}

	public void updatePost(Long id, UpdatePostRequest request, Member member) {
		Post post = getPostById(id);

		validateMemberOwnership(member, post);

		post.setTitle(request.title());
		post.setContent(request.content());

		postRepository.save(post);
	}

	public void removePost(Long id, Member member) {
		Post post = getPostById(id);

		validateMemberOwnership(member, post);

		postRepository.delete(post);
	}

	private Post getPostById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));
	}

	private void validateMemberOwnership(Member member, Post post) {
		if (!isMemberOwnership(member, post)) {
			throw new IllegalArgumentException("게시글에 대한 권한이 없습니다.");
		}
	}

	private boolean isMemberOwnership(Member member, Post post) {
		return post.getOwner().equals(member);
	}

	public void like(Long id, Member member) {
		Post post = getPostById(id);

		if (isMemberOwnership(member, post)) {
			throw new IllegalArgumentException("본인은 게시글에 좋아요를 할 수 없습니다.");
		}

		PostLike like = new PostLike();
		like.setPost(post);
		like.setMember(member);
		postLikeRepository.save(like);

		upLikeCount(post);
		postRepository.save(post);
	}

	public void unlike(Long id, Member member) {
		Post post = getPostById(id);
		PostLike postLike = getPostLike(post, member);
		postLikeRepository.delete(postLike);

		downLikeCount(post);
		postRepository.save(post);
	}

	private PostLike getPostLike(Post post, Member member) {
		return postLikeRepository.findByPostAndMember(post, member)
			.orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));
	}

	private void upLikeCount(Post post) {
		Long postLikeCount = post.getLikeCount();
		post.setLikeCount(postLikeCount - 1);
	}

	private void downLikeCount(Post post) {
		Long postLikeCount = post.getLikeCount();
		post.setLikeCount(postLikeCount + 1);
	}
}

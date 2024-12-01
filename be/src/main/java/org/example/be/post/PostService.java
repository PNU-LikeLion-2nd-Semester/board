package org.example.be.post;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.example.be.user.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final PostLikeRepository postLikeRepository;

	public void writePost(WritePostRequest request, MultipartFile imageFile, Member member) throws IOException {

		String imagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
		String fileName = imageFile != null ? imageFile.getOriginalFilename() : null;
		String newFileName = "";

		if (fileName != null) {
			newFileName = UUID.randomUUID() + "_" + fileName;
			File filePath = new File(imagePath, newFileName);
			imageFile.transferTo(filePath);
		} else {
			newFileName = null;
		}

		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.imageName(newFileName)
			.imagePath(newFileName != null ? imagePath + File.separator + newFileName : null)
			.owner(member)
			.build();

		postRepository.save(post);
	}

	@Transactional(readOnly = true)
	public GetPostResponse readPost(Long id) {
		Post post = getPostById(id);

		GetPostResponse.PostDetail postDetail = new GetPostResponse.PostDetail(post.getId(), post.getTitle(),
			post.getContent(), post.getImagePath());

		return new GetPostResponse(postDetail);
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
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId));
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

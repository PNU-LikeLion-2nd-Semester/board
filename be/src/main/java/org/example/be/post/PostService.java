package org.example.be.post;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.be.post.dto.GetPageResponse;
import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final ImageRepository imageRepository;

	public void writePost(WritePostRequest request, List<MultipartFile> imageFile) throws IOException {

		String imagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";

		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
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

	public GetPostResponse readPost(Long id) {
		Post post = postRepository.fetchPost(id);

		if (post == null) {
			throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
		}

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

	public void updatePost(Long id, UpdatePostRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		post.setTitle(request.title());
		post.setContent(request.content());

		postRepository.save(post);
	}

	public void removePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		postRepository.delete(post);
	}
}

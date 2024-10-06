package org.example.be.post;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;

	public void writePost(WritePostRequest request, MultipartFile imageFile) throws IOException {

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
			.build();

		postRepository.save(post);
	}

	public GetPostResponse readPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		GetPostResponse.PostDetail postDetail = new GetPostResponse.PostDetail(post.getId(), post.getTitle(),
			post.getContent(), post.getImagePath());

		return new GetPostResponse(postDetail);
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

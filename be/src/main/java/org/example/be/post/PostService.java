package org.example.be.post;

import org.example.be.post.dto.WritePostRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public void writePost(WritePostRequest request) {
		Post post = Post.builder()
			.title(request.title())
			.content(request.content())
			.build();

		postRepository.save(post);
	}

	@Transactional
	public void removePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

		postRepository.delete(post);
	}
}

package org.example.be.post;

import org.example.be.post.dto.WritePostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private PostService postService;

	@PostMapping
	public ResponseEntity<String> createPost(@RequestBody WritePostRequest request) {
		postService.writePost(request);
		return ResponseEntity.ok("create");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
		postService.removePost(id);
		return ResponseEntity.ok("delete");
	}
}

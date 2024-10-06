package org.example.be.post;

import java.io.IOException;

import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<String> createPost(@RequestPart WritePostRequest request,
		@RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
		postService.writePost(request, imageFile);
		return ResponseEntity.ok("create");
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetPostResponse> getPost(@PathVariable("id") Long id) {
		GetPostResponse response = postService.readPost(id);
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updatePost(@PathVariable("id") Long id, @RequestBody UpdatePostRequest request) {
		postService.updatePost(id, request);
		return ResponseEntity.ok("update");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
		postService.removePost(id);
		return ResponseEntity.ok("delete");
	}
}

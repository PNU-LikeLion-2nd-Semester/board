package org.example.be.post;

import java.io.IOException;

import org.example.be.post.dto.GetPostResponse;
import org.example.be.post.dto.PostCommentsResponse;
import org.example.be.post.dto.UpdateCommentRequest;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final CommentService commentService;

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
		return ResponseEntity.ok("Post deleted successfully.");
	}

	/**
	 * 기능: 댓글 작성
	 * Method: POST
	 */
	@PostMapping("/{postId}/comments")
	public ResponseEntity<String> uploadComment(
		@PathVariable("postId") Long postId,
		@Valid @RequestBody UpdateCommentRequest request
	) {
		commentService.saveComment(postId, request);
		return ResponseEntity.status(201).body("Comment was successfully uploaded!");
	}

	/**
	 * 기능: 댓글 조회
	 * Method: GET
	 */
	@GetMapping("/{postId}/comments")
	public ResponseEntity<PostCommentsResponse> getCommentsByPostId(@PathVariable("postId") Long postId) {
		PostCommentsResponse response = commentService.findByPostId(postId);
		return ResponseEntity.ok(response);
	}

	/**
	 * 기능: 댓글 수정
	 * Method: PUT
	 */
	@PutMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> modifyCommentById(
		@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId,
		@Valid @RequestBody UpdateCommentRequest request
	) {
		commentService.updateComment(postId, commentId, request);
		return ResponseEntity.ok("Comment was successfully modified!");
	}

	/**
	 * 기능: 댓글 삭제
	 * Method: DELETE
	 */
	@DeleteMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(
		@PathVariable("postId") Long postId,
		@PathVariable("commentId") Long commentId
	) {
		commentService.deleteComment(postId, commentId);
		return ResponseEntity.ok("Comment was successfully deleted!");
	}
}
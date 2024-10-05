package org.example.be.post;

import java.util.List;

import org.example.be.post.dto.PostCommentsResponse;
import org.example.be.post.dto.UpdateCommentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public void saveComment(Long postId, UpdateCommentRequest request) {
		Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
		Comment comment = Comment.builder()
			.content(request.content())
			.post(post)
			.build();
		commentRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public PostCommentsResponse findByPostId(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
		List<Comment> comments = commentRepository.findAllByPostEquals(post);
		return PostCommentsResponse.from(comments);
	}

	public void updateComment(Long postId, Long commentId, UpdateCommentRequest request) {
		Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

		Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new IllegalArgumentException("comment does not belong to the specified post.");
		}

		comment.updateContent(request.content());
	}

	public void deleteComment(Long postId, Long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

		Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new IllegalArgumentException("Comment does not belong to the specified post.");
		}

		commentRepository.delete(comment);
	}
}

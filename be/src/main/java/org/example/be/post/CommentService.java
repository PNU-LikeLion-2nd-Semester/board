package org.example.be.post;

import java.util.List;

import org.example.be.post.dto.PostCommentsResponse;
import org.example.be.post.dto.UpdateCommentRequest;
import org.example.be.user.Member;
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

	public void saveComment(Long postId, UpdateCommentRequest request, Member member) {
		Post post = getPostById(postId);
		String content = request.content();
		Comment comment = Comment.builder()
			.content(content)
			.post(post)
			.owner(member)
			.build();
		commentRepository.save(comment);
	}

	public void addReply(Long postId, Long parentId, AddReplyCommentRequest request, Member member) {
		Post post = getPostById(postId);
		Comment parentComment = getCommentById(parentId);

		validateCommentBelongsToPost(parentComment, post);

		if (parentComment.getParent() != null) {
			throw new IllegalArgumentException("대댓글에는 추가로 대댓글을 작성할 수 없습니다.");
		}

		String content = request.content();
		Comment comment = Comment.builder()
			.content(content)
			.post(parentComment.getPost())
			.parent(parentComment)
			.owner(member)
			.build();

		commentRepository.save(comment);
	}

	@Transactional(readOnly = true)
	public PostCommentsResponse findByPostId(Long postId) {
		Post post = getPostById(postId);
		List<Comment> comments = commentRepository.findAllByPostIdWithPost(post.getId());
		return PostCommentsResponse.from(comments);
	}

	public void updateComment(Long postId, Long commentId, UpdateCommentRequest request, Member member) {
		Post post = getPostById(postId);
		Comment comment = getCommentById(commentId);

		validateCommentBelongsToPost(comment, post);
		validateMemberOwnership(member, comment);

		comment.setContent(request.content());
		commentRepository.save(comment);
	}

	public void deleteComment(Long postId, Long commentId, Member member) {
		Post post = getPostById(postId);
		Comment comment = getCommentById(commentId);

		validateCommentBelongsToPost(comment, post);
		validateMemberOwnership(member, comment);

		commentRepository.delete(comment);
	}

	private Post getPostById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + postId));
	}

	private Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));
	}

	private void validateMemberOwnership(Member member, Comment comment) {
		if (!comment.getOwner().equals(member)) {
			throw new IllegalArgumentException("댓글을 수정하거나 삭제할 권한이 없습니다.");
		}
	}

	private void validateCommentBelongsToPost(Comment comment, Post post) {
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new IllegalArgumentException("댓글이 지정된 게시글에 속하지 않습니다.");
		}
	}
}

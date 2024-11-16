package org.example.be.post.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.be.post.Comment;

public record PostCommentsResponse(
	List<PostComment> postComments
) {
	public static PostCommentsResponse from(List<Comment> comments) {
		List<PostComment> postComments = new ArrayList<>(comments.size());
		for (Comment comment : comments) {
			postComments.add(PostComment.from(comment));
		}
		return new PostCommentsResponse(Collections.unmodifiableList(postComments));
	}

	public record PostComment(
		Long id,
		String content
	) {
		public static PostComment from(Comment comment) {
			return new PostComment(comment.getId(), comment.getContent());
		}
	}
}
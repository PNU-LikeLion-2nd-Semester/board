package org.example.be.post.dto;

public record CommentModifyRequest(
	Long id,
	String content
) {
}
package org.example.be.post;

import jakarta.validation.constraints.NotBlank;

public record AddReplyCommentRequest(
	@NotBlank(message = "Content must not be blank")
	String content
) {
}
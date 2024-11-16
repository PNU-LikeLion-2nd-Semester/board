package org.example.be.post.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
	@NotBlank(message = "Content must not be blank")
	String content
) {
}

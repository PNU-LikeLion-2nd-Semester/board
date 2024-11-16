package org.example.be.post.dto;

public record GetPostResponse(PostDetail post) {

	public record PostDetail(Long id,
							 String title,
							 String content,
							 String imagePath) {
	}
}

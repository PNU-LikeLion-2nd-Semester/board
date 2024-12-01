package org.example.be.post.dto;

import java.util.List;

public record GetPostResponse(PostDetail post) {

	public record PostDetail(Long id,
							 String title,
							 String content,
							 List<String> imagePaths) {
	}
}

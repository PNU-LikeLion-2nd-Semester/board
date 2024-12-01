package org.example.be.post.dto;

import java.util.List;

public record GetPageResponse(
	List<PostDetail> posts,
	int currentPage,
	int totalPages
) {
	public record PostDetail(Long id, String title, String content, String thumbnailPath) {}
}

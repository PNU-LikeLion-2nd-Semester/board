package org.example.be.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.example.be.post.dto.UpdatePostRequest;
import org.example.be.post.dto.WritePostRequest;
import org.example.be.user.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	private Member member;
	private Member anotherMember;
	private Post post;
	private WritePostRequest writePostRequest;
	private UpdatePostRequest updatePostRequest;

	@BeforeEach
	public void setUp() {
		member = Member.builder()
			.id(1L)
			.username("username")
			.password("password")
			.role("USER")
			.build();

		anotherMember = Member.builder()
			.id(2L)
			.username("another")
			.password("password")
			.role("USER")
			.build();

		post = Post.builder()
			.title("title")
			.content("content")
			.owner(member)
			.build();

		updatePostRequest = new UpdatePostRequest("Test Title", "Test Title");
		writePostRequest = new WritePostRequest("Test Title", "Test Content");
	}

	@Test
	@DisplayName("이미지가 있는 게시글 작성 테스트")
	public void testWritePostWithImage() throws IOException {
		MockMultipartFile mockFile = new MockMultipartFile(
			"imageFile",
			"test.jpg",
			"image/jpeg",
			"Test Image Content".getBytes()
		);

		postService.writePost(writePostRequest, mockFile, member);

		String expectedImagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static";
		String savedFileName = mockFile.getOriginalFilename();
		assertNotNull(savedFileName);

		verify(postRepository, times(1)).save(argThat(post ->
			post.getTitle().equals(writePostRequest.title()) &&
				post.getContent().equals(writePostRequest.content()) &&
				post.getImageName().contains(savedFileName) &&
				post.getImagePath().startsWith(expectedImagePath) &&
				post.getOwner().equals(member)
		));
	}

	@Test
	@DisplayName("이미지 없이 게시글 작성 테스트")
	public void testWritePostWithoutImage() throws IOException {
		postService.writePost(writePostRequest, null, member);

		verify(postRepository, times(1)).save(argThat(post ->
			post.getTitle().equals(writePostRequest.title()) &&
				post.getContent().equals(writePostRequest.content()) &&
				post.getImageName() == null &&
				post.getImagePath() == null &&
				post.getOwner().equals(member)
		));
	}

	@Test
	@DisplayName("게시글 수정 성공 테스트")
	public void testUpdatePost_Success() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		postService.updatePost(post.getId(), updatePostRequest, member);

		assertEquals(updatePostRequest.title(), post.getTitle());
		assertEquals(updatePostRequest.content(), post.getContent());

		verify(postRepository, times(1)).save(post);
	}

	@Test
	@DisplayName("권한이 없는 경우 게시글 수정 테스트")
	public void testUpdatePost_NoPermission() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		assertThrows(IllegalArgumentException.class,
			() -> postService.updatePost(post.getId(), updatePostRequest, anotherMember));
	}

	@Test
	@DisplayName("게시글을 찾을 수 없는 경우 수정 테스트")
	public void testUpdatePost_PostNotFound() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class,
			() -> postService.updatePost(post.getId(), updatePostRequest, member));
	}

	@Test
	@DisplayName("게시글 삭제 성공 테스트")
	public void testRemovePost_Success() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		postService.removePost(post.getId(), member);

		verify(postRepository, times(1)).delete(post);
	}

	@Test
	@DisplayName("권한이 없는 경우 게시글 삭제 테스트")
	public void testRemovePost_NoPermission() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		assertThrows(IllegalArgumentException.class, () -> postService.removePost(post.getId(), anotherMember));
	}
}
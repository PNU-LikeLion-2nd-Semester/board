package org.example.be.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.be.user.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

	@Mock
	private PostRepository postRepository;
	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentService commentService;

	private Member member;
	private Post post;
	private Comment comment;

	private AddReplyCommentRequest addReplyCommentRequest;

	@BeforeEach
	public void setUp() {
		member = Member.builder()
			.id(1L)
			.username("username")
			.password("password")
			.role("USER")
			.build();

		post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.owner(member)
			.build();

		comment = Comment.builder()
			.id(1L)
			.post(post)
			.content("content")
			.build();

		addReplyCommentRequest = new AddReplyCommentRequest("reply");
	}

	@Test
	@DisplayName("대댓글 작성 성공 테스트")
	public void testAddReplyComment_Success() {
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
		when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

		Comment expectedComment = Comment.builder()
			.content(addReplyCommentRequest.content())
			.post(post)
			.parent(comment)
			.build();

		assertDoesNotThrow(
			() -> commentService.addReply(post.getId(), comment.getId(), addReplyCommentRequest, member));

		verify(commentRepository, times(1)).save(expectedComment);
	}
}
package org.example.be.post;

import java.util.Optional;

import org.example.be.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
}

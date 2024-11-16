package org.example.be.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPostEquals(Post post);

	@Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :postId")
	List<Comment> findAllByPostIdWithPost(@Param("postId") Long postId);
}

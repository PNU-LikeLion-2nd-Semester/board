package org.example.be.post;

import java.util.Optional;

import org.example.be.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	Optional<PostLike> findByPostAndMember(Post post, Member member);
}

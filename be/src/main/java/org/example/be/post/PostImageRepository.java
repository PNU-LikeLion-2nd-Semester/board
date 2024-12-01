package org.example.be.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImageRelation, Long> {
}

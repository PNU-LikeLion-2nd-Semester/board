package org.example.be.post;

import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public Post fetchPost(Long postId) {

		QPost post = QPost.post;
		QPostImageRelation postImageRelation = QPostImageRelation.postImageRelation;
		QImage image = QImage.image;

		return queryFactory.selectFrom(post)
			.leftJoin(post.postImageRelations, postImageRelation).fetchJoin()
			.leftJoin(postImageRelation.image, image).fetchJoin()
			.where(post.id.eq(postId))
			.fetchOne();
	}
}

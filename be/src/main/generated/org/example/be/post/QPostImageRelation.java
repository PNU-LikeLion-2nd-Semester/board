package org.example.be.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImageRelation is a Querydsl query type for PostImageRelation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostImageRelation extends EntityPathBase<PostImageRelation> {

    private static final long serialVersionUID = 1305735110L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImageRelation postImageRelation = new QPostImageRelation("postImageRelation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QImage image;

    public final QPost post;

    public QPostImageRelation(String variable) {
        this(PostImageRelation.class, forVariable(variable), INITS);
    }

    public QPostImageRelation(Path<? extends PostImageRelation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImageRelation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImageRelation(PathMetadata metadata, PathInits inits) {
        this(PostImageRelation.class, metadata, inits);
    }

    public QPostImageRelation(Class<? extends PostImageRelation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
    }

}


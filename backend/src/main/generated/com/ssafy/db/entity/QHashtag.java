package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHashtag is a Querydsl query type for Hashtag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHashtag extends EntityPathBase<Hashtag> {

    private static final long serialVersionUID = 143745482L;

    public static final QHashtag hashtag = new QHashtag("hashtag");

    public final NumberPath<Long> cnt = createNumber("cnt", Long.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath nm = createString("nm");

    public final DateTimePath<java.time.LocalDateTime> recent = createDateTime("recent", java.time.LocalDateTime.class);

    public QHashtag(String variable) {
        super(Hashtag.class, forVariable(variable));
    }

    public QHashtag(Path<? extends Hashtag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashtag(PathMetadata metadata) {
        super(Hashtag.class, metadata);
    }

}


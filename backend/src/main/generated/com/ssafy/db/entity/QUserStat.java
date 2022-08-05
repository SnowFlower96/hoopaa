package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserStat is a Querydsl query type for UserStat
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserStat extends EntityPathBase<UserStat> {

    private static final long serialVersionUID = -254500223L;

    public static final QUserStat userStat = new QUserStat("userStat");

    public final NumberPath<Integer> draw = createNumber("draw", Integer.class);

    public final NumberPath<Integer> exp = createNumber("exp", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> king = createNumber("king", Integer.class);

    public final NumberPath<Integer> lose = createNumber("lose", Integer.class);

    public final NumberPath<Integer> penalty = createNumber("penalty", Integer.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final NumberPath<Integer> win = createNumber("win", Integer.class);

    public QUserStat(String variable) {
        super(UserStat.class, forVariable(variable));
    }

    public QUserStat(Path<? extends UserStat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserStat(PathMetadata metadata) {
        super(UserStat.class, metadata);
    }

}


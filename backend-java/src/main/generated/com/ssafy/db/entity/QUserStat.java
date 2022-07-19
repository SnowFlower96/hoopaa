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

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> draw = createNumber("draw", Long.class);

    public final NumberPath<Long> exp = createNumber("exp", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> king = createNumber("king", Long.class);

    public final NumberPath<Long> lose = createNumber("lose", Long.class);

    public final NumberPath<Long> penalty = createNumber("penalty", Long.class);

    public final NumberPath<Long> total = createNumber("total", Long.class);

    public final NumberPath<Long> win = createNumber("win", Long.class);

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


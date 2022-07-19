package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath access_tk = createString("access_tk");

    public final StringPath em = createString("em");

    public final BooleanPath em_auth = createBoolean("em_auth");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> join_dt = createDateTime("join_dt", java.time.LocalDateTime.class);

    public final StringPath leave_dt = createString("leave_dt");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath modify_dt = createString("modify_dt");

    public final StringPath nnm = createString("nnm");

    public final StringPath pwd = createString("pwd");

    public final StringPath refresh_tk = createString("refresh_tk");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


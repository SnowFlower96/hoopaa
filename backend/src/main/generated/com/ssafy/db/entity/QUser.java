package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final StringPath em = createString("em");

    public final BooleanPath em_auth = createBoolean("em_auth");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> join_dt = createDateTime("join_dt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> leave_dt = createDateTime("leave_dt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modify_dt = createDateTime("modify_dt", java.time.LocalDateTime.class);

    public final StringPath nnm = createString("nnm");

    public final StringPath pwd = createString("pwd");

    public final StringPath refresh_tk = createString("refresh_tk");

    public final QUserStat userStat;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userStat = inits.isInitialized("userStat") ? new QUserStat(forProperty("userStat")) : null;
    }

}


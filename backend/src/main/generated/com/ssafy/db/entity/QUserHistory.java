package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHistory is a Querydsl query type for UserHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserHistory extends EntityPathBase<UserHistory> {

    private static final long serialVersionUID = 1625308487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHistory userHistory = new QUserHistory("userHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHost = createBoolean("isHost");

    public final BooleanPath isKing = createBoolean("isKing");

    public final NumberPath<Long> roomId = createNumber("roomId", Long.class);

    public final QRoomInfo roomInfo;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Integer> userPos = createNumber("userPos", Integer.class);

    public QUserHistory(String variable) {
        this(UserHistory.class, forVariable(variable), INITS);
    }

    public QUserHistory(Path<? extends UserHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHistory(PathMetadata metadata, PathInits inits) {
        this(UserHistory.class, metadata, inits);
    }

    public QUserHistory(Class<? extends UserHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomInfo = inits.isInitialized("roomInfo") ? new QRoomInfo(forProperty("roomInfo"), inits.get("roomInfo")) : null;
    }

}


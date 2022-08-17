package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomInfo is a Querydsl query type for RoomInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomInfo extends EntityPathBase<RoomInfo> {

    private static final long serialVersionUID = -161640981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomInfo roomInfo = new QRoomInfo("roomInfo");

    public final NumberPath<Short> agree = createNumber("agree", Short.class);

    public final NumberPath<Integer> curNum = createNumber("curNum", Integer.class);

    public final NumberPath<Short> disagree = createNumber("disagree", Short.class);

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> invalid = createNumber("invalid", Short.class);

    public final NumberPath<Long> kingId = createNumber("kingId", Long.class);

    public final StringPath log = createString("log");

    public final NumberPath<Integer> maxNum = createNumber("maxNum", Integer.class);

    public final NumberPath<Integer> phase = createNumber("phase", Integer.class);

    public final StringPath pwd = createString("pwd");

    public final QRoomDescription roomDescription;

    public final QRoomPanel roomPanel;

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath thumbUrl = createString("thumbUrl");

    public final QUser userHost;

    public final QUser userKing;

    public final NumberPath<Short> winner = createNumber("winner", Short.class);

    public QRoomInfo(String variable) {
        this(RoomInfo.class, forVariable(variable), INITS);
    }

    public QRoomInfo(Path<? extends RoomInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomInfo(PathMetadata metadata, PathInits inits) {
        this(RoomInfo.class, metadata, inits);
    }

    public QRoomInfo(Class<? extends RoomInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomDescription = inits.isInitialized("roomDescription") ? new QRoomDescription(forProperty("roomDescription")) : null;
        this.roomPanel = inits.isInitialized("roomPanel") ? new QRoomPanel(forProperty("roomPanel")) : null;
        this.userHost = inits.isInitialized("userHost") ? new QUser(forProperty("userHost"), inits.get("userHost")) : null;
        this.userKing = inits.isInitialized("userKing") ? new QUser(forProperty("userKing"), inits.get("userKing")) : null;
    }

}


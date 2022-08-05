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

    public final NumberPath<Integer> cate = createNumber("cate", Integer.class);

    public final NumberPath<Integer> curNum = createNumber("curNum", Integer.class);

    public final NumberPath<Integer> hash1 = createNumber("hash1", Integer.class);

    public final NumberPath<Integer> hash2 = createNumber("hash2", Integer.class);

    public final NumberPath<Integer> hash3 = createNumber("hash3", Integer.class);

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isSys = createNumber("isSys", Integer.class);

    public final NumberPath<Integer> maxNum = createNumber("maxNum", Integer.class);

    public final NumberPath<Integer> phase = createNumber("phase", Integer.class);

    public final StringPath pwd = createString("pwd");

    public final QRoomHistory roomHistory;

    public final QRoomStatus roomStatus;

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath subtitle = createString("subtitle");

    public final StringPath thumbUrl = createString("thumbUrl");

    public final StringPath title = createString("title");

    public final QUser user;

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
        this.roomHistory = inits.isInitialized("roomHistory") ? new QRoomHistory(forProperty("roomHistory")) : null;
        this.roomStatus = inits.isInitialized("roomStatus") ? new QRoomStatus(forProperty("roomStatus")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


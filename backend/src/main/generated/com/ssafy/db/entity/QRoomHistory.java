package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoomHistory is a Querydsl query type for RoomHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomHistory extends EntityPathBase<RoomHistory> {

    private static final long serialVersionUID = -1806510537L;

    public static final QRoomHistory roomHistory = new QRoomHistory("roomHistory");

    public final NumberPath<Integer> agree = createNumber("agree", Integer.class);

    public final NumberPath<Integer> disagree = createNumber("disagree", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> end_time = createDateTime("end_time", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> invalid = createNumber("invalid", Integer.class);

    public final StringPath log = createString("log");

    public final NumberPath<Integer> winner = createNumber("winner", Integer.class);

    public QRoomHistory(String variable) {
        super(RoomHistory.class, forVariable(variable));
    }

    public QRoomHistory(Path<? extends RoomHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomHistory(PathMetadata metadata) {
        super(RoomHistory.class, metadata);
    }

}


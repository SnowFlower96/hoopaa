package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoomStatus is a Querydsl query type for RoomStatus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomStatus extends EntityPathBase<RoomStatus> {

    private static final long serialVersionUID = -426467857L;

    public static final QRoomStatus roomStatus = new QRoomStatus("roomStatus");

    public final NumberPath<Integer> agree = createNumber("agree", Integer.class);

    public final NumberPath<Long> agree_1 = createNumber("agree_1", Long.class);

    public final NumberPath<Long> agree_2 = createNumber("agree_2", Long.class);

    public final NumberPath<Long> agree_3 = createNumber("agree_3", Long.class);

    public final NumberPath<Long> agree_4 = createNumber("agree_4", Long.class);

    public final NumberPath<Long> agree_5 = createNumber("agree_5", Long.class);

    public final NumberPath<Integer> disagree = createNumber("disagree", Integer.class);

    public final NumberPath<Long> disagree_1 = createNumber("disagree_1", Long.class);

    public final NumberPath<Long> disagree_2 = createNumber("disagree_2", Long.class);

    public final NumberPath<Long> disagree_3 = createNumber("disagree_3", Long.class);

    public final NumberPath<Long> disagree_4 = createNumber("disagree_4", Long.class);

    public final NumberPath<Long> disagree_5 = createNumber("disagree_5", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QRoomStatus(String variable) {
        super(RoomStatus.class, forVariable(variable));
    }

    public QRoomStatus(Path<? extends RoomStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomStatus(PathMetadata metadata) {
        super(RoomStatus.class, metadata);
    }

}


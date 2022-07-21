package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoomInfo;
import com.ssafy.db.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomInfoRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QRoomInfo qRoomInfo = QRoomInfo.roomInfo;

    public List<RoomInfo> findAll(){

        List<RoomInfo> roomInfo= jpaQueryFactory.selectFrom(qRoomInfo).where(qRoomInfo.phase.in(0,1))
                .orderBy(qRoomInfo.cur_num.desc()).fetch();
       return roomInfo;
    }
}

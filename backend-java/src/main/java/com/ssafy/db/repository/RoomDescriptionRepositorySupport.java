package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoomDescription;
import com.ssafy.db.entity.RoomDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDescriptionRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QRoomDescription qRoomDescription=QRoomDescription.roomDescription;

    public List<RoomDescription> searchBy (String keyword){
        List<RoomDescription> searchBy = jpaQueryFactory.selectFrom(qRoomDescription)
                .where(qRoomDescription.subtitle.like("%"+keyword+"%").or(qRoomDescription.title.like("%"+keyword+"%")))
                .orderBy(qRoomDescription.roomInfo.cur_num.desc()).fetch();
        return searchBy;
    }
}

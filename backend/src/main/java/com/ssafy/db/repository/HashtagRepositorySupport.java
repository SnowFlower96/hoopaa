package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoomHashtag;
import com.ssafy.db.entity.RoomHashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HashtagRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QRoomHashtag roomHashtag = QRoomHashtag.roomHashtag;

    public Optional<RoomHashtag> findHashtagByNm(String nm) {
        RoomHashtag roomHashtag = jpaQueryFactory.select(this.roomHashtag).from(this.roomHashtag)
                .where(this.roomHashtag.nm.eq(nm)).fetchOne();
        if (roomHashtag == null) {
            return Optional.empty();
        }

        return Optional.of(roomHashtag);
    }

}

package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUserHistory;
import com.ssafy.db.entity.UserHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserHistoryRepositorySupport {

    QUserHistory qUserHistory;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public Optional<List<UserHistory>> findUserHistoryByUserId(Long user_id) {
        List<UserHistory> userHistoryList = jpaQueryFactory.select(qUserHistory).from(qUserHistory)
                .where(qUserHistory.userId.eq(user_id)).fetch();
        if (userHistoryList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(userHistoryList);
    }

    public Optional<UserHistory> findUserHistoryByUserIdAndRoomId(Long user_id, Long room_id){
        UserHistory userHistory = jpaQueryFactory.select(qUserHistory).from(qUserHistory)
                .where(qUserHistory.userId.eq(user_id).and(qUserHistory.roomId.eq(room_id))).fetchOne();
        if(userHistory==null) return  Optional.empty();
        return Optional.ofNullable(userHistory);
    }

}

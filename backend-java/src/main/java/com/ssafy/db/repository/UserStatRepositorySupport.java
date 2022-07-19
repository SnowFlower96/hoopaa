package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUserStat;
import com.ssafy.db.entity.UserStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserStatRepositorySupport {

    QUserStat qUserStat = QUserStat.userStat;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public Optional<UserStat> findUserStatById(Long id){
        UserStat userStat = jpaQueryFactory.select(qUserStat).from(qUserStat)
                .where(qUserStat.id.eq(id)).fetchOne();
        if(userStat==null) return Optional.empty();
        return Optional.ofNullable(userStat);
    }
}

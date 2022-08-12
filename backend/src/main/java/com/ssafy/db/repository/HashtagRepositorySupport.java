package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Hashtag;
import com.ssafy.db.entity.QHashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HashtagRepositorySupport{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QHashtag qHashtag = QHashtag.hashtag;

    public Optional<Hashtag> findHashtagByNm(String nm){
        Hashtag hashtag = jpaQueryFactory.select(qHashtag).from(qHashtag)
                .where(qHashtag.nm.eq(nm)).fetchOne();
        if(hashtag == null) {
            System.out.println("없음");
            return Optional.empty();
        }

        return Optional.ofNullable(hashtag);

    }

}

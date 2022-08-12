package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<RoomHashtag, Integer> {
    Optional<RoomHashtag> findHashtagByNm(String nm);
}

package com.ssafy.db.repository;

import com.ssafy.db.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 유저 전적 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의
 */
@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    Optional<List<UserHistory>> findUserHistoryByUserId(Long user_id);

}

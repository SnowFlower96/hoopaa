package com.ssafy.db.repository;

import com.ssafy.db.entity.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatRepository extends JpaRepository<UserStat, Long > {
    Optional<UserStat> findById(Long id);
}

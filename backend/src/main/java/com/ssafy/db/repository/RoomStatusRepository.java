package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
}

package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, Long> {
    Optional<RoomInfo> findRoomInfoById(Long roomId);
    Optional<RoomInfo> findRoomInfoByHostId(Long hostId);
}

package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDescriptionRepository extends JpaRepository<RoomDescription, Long> {



}

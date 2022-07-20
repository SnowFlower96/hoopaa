package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDescriptionRepository extends JpaRepository<RoomDescription,Long> {

    List<RoomDescription> searchBy();
}

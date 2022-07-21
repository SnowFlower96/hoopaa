package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RoomInfoService {

    List<RoomInfo> findAll();
    List<RoomInfo> searchBy(String keyword);
    List<RoomInfo> findByCate(int cate);
}

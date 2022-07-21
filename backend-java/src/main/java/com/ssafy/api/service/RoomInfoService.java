package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomInfoService {

    Page<RoomInfo> findAll(int page, int size);
    List<RoomInfo> searchBy(String keyword);
    List<RoomInfo> findByCate(int cate);
}

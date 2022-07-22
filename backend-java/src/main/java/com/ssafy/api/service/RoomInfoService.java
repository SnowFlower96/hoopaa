package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.awt.print.Pageable;
import java.util.List;

public interface RoomInfoService {

    List<RoomInfo> findAll(long now);
    List<RoomInfo> searchBy(String keyword,long now);
    List<RoomInfo> findByCate(int cate,long now);
}

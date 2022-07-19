package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;

import java.util.List;

public interface RoomInfoService {

    List<RoomInfo> findAll();
}

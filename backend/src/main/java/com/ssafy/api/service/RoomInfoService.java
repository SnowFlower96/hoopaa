package com.ssafy.api.service;

import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;

import java.io.IOException;
import java.util.List;

public interface RoomInfoService {

    List<RoomInfoDto> findAll() throws IOException;

    List<RoomInfoDto> searchBy(String keyword) throws IOException;

    List<RoomInfoDto> findByCate(int cate) throws IOException;

    List<RoomInfoDto> roomInfoToDto(List<RoomInfo> roomInfoList) throws IOException;

}

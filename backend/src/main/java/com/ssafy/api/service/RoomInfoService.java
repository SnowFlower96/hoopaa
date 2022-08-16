package com.ssafy.api.service;

import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.awt.print.Pageable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RoomInfoService {

    List<RoomInfoDto> findAll() throws IOException;
    List<RoomInfoDto> searchBy(String keyword) throws IOException;
    List<RoomInfoDto> findByCate(int cate) throws IOException;
    List<RoomInfoDto> roomInfoToDto(List<RoomInfo> roomInfoList) throws IOException;

}

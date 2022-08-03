package com.ssafy.api.service;


import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;

import java.util.NoSuchElementException;

public interface RoomService {

    public RoomInfoDto createRoom(RoomOpenReq roomOpenInfo);

    public RoomInfoDto updatePhaseByRoomId(Long id, int phase);

    public void finishRoom(RoomCloseReq roomCloseReq);

    public RoomInfoDto enterDebate(RoomEnterReq roomEnterReq) throws NoSuchElementException;


    public int findHashtagId(String nm);
}

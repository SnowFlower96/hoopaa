package com.ssafy.api.service;


import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.User;

import java.util.NoSuchElementException;

public interface RoomService {

    RoomInfoDto createRoom(RoomOpenReq roomOpenInfo);

//    boolean enterRoom(RoomEnterReq roomEnterReq, String userEm) throws NoSuchElementException;
    RoomInfoDto findRoomByRoomId(Long roomId);

    RoomInfoDto findRoomBySessionId(String sessionId);

    RoomInfoDto updatePhaseByRoomId(Long id, int phase);

    void finishRoom(RoomCloseReq roomCloseReq);


    int findHashtagId(String nm);
}

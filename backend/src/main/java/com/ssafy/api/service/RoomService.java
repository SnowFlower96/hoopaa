package com.ssafy.api.service;


import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;

public interface RoomService {

    RoomInfoDto createRoom(RoomOpenReq roomOpenInfo);

    void updatePhaseByRoomId(Long id, int phase);

    void finishRoom(VRoom session);

    int findHashtagId(String nm);

}
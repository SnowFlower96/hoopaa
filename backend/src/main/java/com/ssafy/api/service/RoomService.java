package com.ssafy.api.service;


import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.common.data.VRoom;
import com.ssafy.db.dto.RoomInfoDto;

public interface RoomService {

    RoomInfoDto createRoom(RoomOpenReq roomOpenInfo);

    Boolean updatePhaseByRoomId(Long id, int phase);

    void finishRoom(VRoom session);

    int findHashtagId(String nm);

}
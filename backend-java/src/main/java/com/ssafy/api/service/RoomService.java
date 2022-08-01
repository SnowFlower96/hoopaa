package com.ssafy.api.service;


import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;

public interface RoomService {

    public RoomInfoDto openRoom(RoomOpenReq roomOpenInfo);

    public RoomInfoDto updateRoomByRoomId(Long id, int phase);

    public void closeRoom(RoomCloseReq roomCloseReq);


    public int findHashtagId(String nm);
}

package com.ssafy.api.service;


import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.entity.RoomInfo;

public interface RoomService {

    public RoomInfo openRoom(RoomOpenReq roomOpenInfo);

    public void closeRoomByRoomId(int id);

    public int findHashtagId(String nm);
}

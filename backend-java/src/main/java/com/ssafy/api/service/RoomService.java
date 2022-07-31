package com.ssafy.api.service;


import com.ssafy.api.request.RoomOpenReq;

public interface RoomService {

    public void openRoom(RoomOpenReq roomOpenInfo);

    public void closeRoomByRoomId(int id);

    public int findHashtagId(String nm);
}

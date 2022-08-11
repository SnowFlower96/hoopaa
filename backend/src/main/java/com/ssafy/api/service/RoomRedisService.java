package com.ssafy.api.service;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.redis.entity.VRoom;

public interface RoomRedisService {

    // 해당 토론방이 있는지
    boolean isExist(String sessionID);

    // RoomOpenReq 를 바탕으로 VRoom 객체를 Redis 에 저장
    boolean createVRoom(RoomOpenReq roomOpenReq);

    // 해당 토론방 정보 반환
    VRoom readVRoom(String sessionID);

    // 토론방 정보 업데이트
    boolean updateVRoom(VRoom vRoom);

    // 토론방 정보 삭제
    boolean deleteVRoom(String sessionID);

}

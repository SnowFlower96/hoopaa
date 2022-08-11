package com.ssafy.api.service;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.redis.entity.VRoom;
import com.ssafy.redis.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("RoomRedisService")
public class RoomRedisServiceImpl implements RoomRedisService{

    @Autowired
    RoomService roomService;

    @Autowired
    RedisRepository redisRepository;

    @Override
    public boolean isExist(String sessionID) {
        return redisRepository.findById(sessionID).isPresent();
    }

    @Override
    public boolean createVRoom(RoomOpenReq roomOpenReq) {
        return false;
    }

    @Override
    public VRoom readVRoom(String sessionID) {
        return null;
    }

    @Override
    public boolean updateVRoom(VRoom vRoom) {
        return false;
    }

    @Override
    public boolean deleteVRoom(String sessionID) {
        return false;
    }
}

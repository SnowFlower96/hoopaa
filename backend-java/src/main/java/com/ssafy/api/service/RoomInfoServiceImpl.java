package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RoomInfoService")
public class RoomInfoServiceImpl implements RoomInfoService{

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    RoomInfoRepositorySupport roomInfoRepositorySupport;

    @Override
    public List<RoomInfo> findAll() {
        List<RoomInfo> roomInfos=roomInfoRepositorySupport.findAll();
        return roomInfos;
    }
}

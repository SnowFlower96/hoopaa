package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        List<RoomInfo> roomInfo= roomInfoRepositorySupport.findAll();
        return roomInfo;
    }

    @Override
    public List<RoomInfo> searchBy(String keyword) {
        List<RoomInfo> searchBy=roomInfoRepositorySupport.searchBy(keyword);
        return searchBy;
    }

    @Override
    public List<RoomInfo> findByCate(int cate) {
        List<RoomInfo> findByCate=roomInfoRepositorySupport.findByCate(cate);
        return findByCate;
    }
}

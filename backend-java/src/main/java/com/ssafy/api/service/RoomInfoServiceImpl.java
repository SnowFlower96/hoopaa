package com.ssafy.api.service;

import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.RoomInfoRepository;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service("RoomInfoService")
public class RoomInfoServiceImpl implements RoomInfoService{

//    @Autowired
//    RoomInfoRepository roomInfoRepository;

    @Autowired
    RoomInfoRepositorySupport roomInfoRepositorySupport;

    @Override
    public List<RoomInfo> findAll(long now) {
        System.out.println("service");
        List<RoomInfo> roomInfo= roomInfoRepositorySupport.findAll(now);
        return roomInfo;
    }

    @Override
    public List<RoomInfo> searchBy(String keyword,long now) {
        List<RoomInfo> searchBy=roomInfoRepositorySupport.searchBy(keyword,now);
        return searchBy;
    }

    @Override
    public List<RoomInfo> findByCate(int cate,long now) {
        List<RoomInfo> findByCate=roomInfoRepositorySupport.findByCate(cate,now);
        return findByCate;
    }
}

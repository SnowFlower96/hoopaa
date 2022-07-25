package com.ssafy.api.service;

import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;
import com.ssafy.db.repository.RoomInfoRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("RoomInfoService")
public class RoomInfoServiceImpl implements RoomInfoService{

    @Autowired
    RoomInfoRepositorySupport roomInfoRepositorySupport;

    @Override
    public List<RoomInfoDto> findAll(long now) {
        List<RoomInfoDto> roomInfo= new LinkedList<>();
        List<RoomInfo> room=roomInfoRepositorySupport.findAll(now);
        for(RoomInfo r:room)
            roomInfo.add(new RoomInfoDto(r));
        return roomInfo;
    }

    @Override
    public List<RoomInfoDto> searchBy(String keyword,long now) {
        List<RoomInfoDto> searchBy=new LinkedList<>();
        List<RoomInfo> room = roomInfoRepositorySupport.searchBy(keyword,now);
        for(RoomInfo r:room)
            searchBy.add(new RoomInfoDto(r));
        return searchBy;
    }

    @Override
    public List<RoomInfoDto> findByCate(int cate,long now) {
        List<RoomInfoDto> findByCate=new LinkedList<>();
        List<RoomInfo> room = roomInfoRepositorySupport.findByCate(cate,now);
        for(RoomInfo r:room)
            findByCate.add(new RoomInfoDto(r));
        return findByCate;
    }
}

package com.ssafy.api.controller;

import com.ssafy.api.service.RoomInfoService;
import com.ssafy.db.entity.RoomInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "토론방 조회 API", tags = {"Rooms"})
@RestController
@RequestMapping("/api/v1/list")
public class RoomInfoController {

    @Autowired
    RoomInfoService roomInfoService;

    @GetMapping("/all")
    @ApiOperation(value = "전체 토론방 조회")
    public List<RoomInfo> findAll(){
        return roomInfoService.findAll();
    }
    @GetMapping("/search/{keyword}")
    @ApiOperation(value="토론방 검색")
    public List<RoomInfo> searchBy(@PathVariable String keyword){
        return roomInfoService.searchBy(keyword);
    }

    @GetMapping("/{cate}")
    @ApiOperation(value="카테고리별 검색")
    public List<RoomInfo> findByCate(@PathVariable int cate){
        return roomInfoService.findByCate(cate);
    }
}

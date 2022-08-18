package com.ssafy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.response.JsonRes;
import com.ssafy.api.service.RoomInfoService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api(value = "토론방 조회 API", tags = {"Rooms"})
@RestController
@RequestMapping("/api/v1/list")
public class RoomInfoController {

    @Autowired
    RoomInfoService roomInfoService;
    @Autowired
    ObjectMapper mapper;

    @GetMapping("/all")
    @ApiOperation(value = "전체 토론방 조회")
    public ResponseEntity<? extends BaseResponseBody> findAll() throws IOException {
        List<RoomInfoDto> findAll = roomInfoService.findAll();
        String findAllString = mapper.writeValueAsString(findAll);
        return ResponseEntity.ok(JsonRes.of(200, "success", findAllString));
    }

    @GetMapping("/search/{keyword}")
    @ApiOperation(value = "토론방 검색")
    public ResponseEntity<? extends BaseResponseBody> searchBy(@PathVariable String keyword) throws IOException {
        List<RoomInfoDto> searchBy = roomInfoService.searchBy(keyword);
        String searchByString = mapper.writeValueAsString(searchBy);
        return ResponseEntity.ok(JsonRes.of(200, "success", searchByString));
    }

    @GetMapping("/{cate}")
    @ApiOperation(value = "카테고리별 검색")
    public ResponseEntity<? extends BaseResponseBody> findByCate(@PathVariable int cate) throws IOException {
        List<RoomInfoDto> findByCate = roomInfoService.findByCate(cate);
        String findByCateString = mapper.writeValueAsString(findByCate);
        return ResponseEntity.ok(JsonRes.of(200, "success", findByCateString));
    }

}

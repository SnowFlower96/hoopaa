package com.ssafy.api.controller;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/room")
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;


    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> openRoom(@RequestBody RoomOpenReq openInfo){

        roomService.openRoom(openInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}

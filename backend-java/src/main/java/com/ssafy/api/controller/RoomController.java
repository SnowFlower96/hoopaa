package com.ssafy.api.controller;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/room")
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;


    @PostMapping()
    public ResponseEntity<? extends BaseResponseBody> openRoom(@RequestBody RoomOpenReq openInfo){

        RoomInfo roomInfo = roomService.openRoom(openInfo);
        System.out.println(roomInfo.getId());
        return ResponseEntity.status(200).body(RoomRes.of(200, "Success",roomInfo));
    }

    @PutMapping()
    public ResponseEntity<? extends BaseResponseBody> startRoom(@RequestParam int room_id){
        return null;
    }


}

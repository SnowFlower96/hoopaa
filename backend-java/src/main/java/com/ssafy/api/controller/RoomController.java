package com.ssafy.api.controller;

import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.entity.RoomInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/room")
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;



    @PostMapping()
    @ApiOperation(value = "토론 방 오픈", notes = "토론 방을 세팅하고 모집 중 상태로 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openRoom(@RequestBody RoomOpenReq openInfo){

        RoomInfoDto roomInfoDto = roomService.openRoom(openInfo);

        return ResponseEntity.status(200).body(RoomRes.of(200, "Success",roomInfoDto));
    }

    @PutMapping("/start/{room_id}")
    @ApiOperation(value = "토론 시작", notes = "토론방을 모집중에서 시작으로 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 411, message = "시작 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> startRoom(@RequestParam Long room_id){

        RoomInfoDto roomInfoDto = roomService.updateRoomByRoomId(room_id, 1);
        if(roomInfoDto == null){
            return ResponseEntity.status(200).body(RoomRes.of(411, "already started or finished"));
        }
        return ResponseEntity.status(200).body(RoomRes.of(200, "debate start", roomInfoDto));
    }

    @PutMapping("/vote/{room_id}")
    @ApiOperation(value = "투표 시작", notes = "토론 종료 전 토론 결과 투표")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 411, message = "투표 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> voteStart(@RequestParam Long room_id){

        RoomInfoDto roomInfoDto = roomService.updateRoomByRoomId(room_id, 2);
        if(roomInfoDto == null){
            return ResponseEntity.status(200).body(RoomRes.of(411, "Voting has already started or ended"));
        }
        return ResponseEntity.status(200).body(RoomRes.of(200, "vote start"));

    }

    @DeleteMapping()
    @ApiOperation(value = "종료", notes = "토론 종료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 411, message = "종료 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeRoom(@RequestBody RoomCloseReq roomCloseReq){

        roomService.closeRoom(roomCloseReq);

        return ResponseEntity.status(200).body(RoomRes.of(200, "vote start"));

    }

}

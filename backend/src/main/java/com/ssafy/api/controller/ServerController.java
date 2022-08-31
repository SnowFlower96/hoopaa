package com.ssafy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.response.StringRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.vidu.VUserInfo;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/sync")
@RestController
@Slf4j
public class ServerController {

    @Autowired
    RoomService roomService;

    @Autowired
    ObjectMapper mapper;

    @PostMapping
    @ApiOperation(value = "동기화", notes = "OpenVidu 서버와의 동기화")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> syncServer() throws OpenViduJavaClientException, OpenViduHttpException {
        roomService.syncServer();
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

}

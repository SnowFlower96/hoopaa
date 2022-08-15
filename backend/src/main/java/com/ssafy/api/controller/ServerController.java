package com.ssafy.api.controller;

import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/sync")
@RestController
@Slf4j
public class ServerController {

    @Autowired
    RoomService roomService;

    @PostMapping
    @ApiOperation(value = "동기화", notes = "OpenVidu 서버와의 동기화")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> syncServer() {
        roomService.syncServer();
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @PostMapping("/init/{pwd}")
    @ApiOperation(value = "초기화")
    public ResponseEntity<? extends BaseResponseBody> initServer(@PathVariable String pwd) throws OpenViduJavaClientException, OpenViduHttpException {
        if (!pwd.equals("ssafy")) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Wrong password"));
        roomService.initServer();
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

}

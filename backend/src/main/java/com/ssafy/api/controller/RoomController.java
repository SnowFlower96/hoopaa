package com.ssafy.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.response.JsonRes;
import com.ssafy.api.response.StringRes;
import com.ssafy.api.response.VTokenRes;
import com.ssafy.api.service.RoomInfoService;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.UserInfoDto;
import com.ssafy.db.entity.User;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/api/v1/room")
@RestController
@Slf4j
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    RoomInfoService roomInfoService;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "토론 방 생성", notes = "토론 방을 세팅하고 모집 중 상태로 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 405, message = "이미 생성된 토론방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openRoom(@ApiIgnore Authentication authentication,
                                                               @RequestBody @ApiParam(value = "방 정보", required = true) RoomOpenReq roomOpenReq) throws IOException {
        // Access Token 에서 유저 ID 추출
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = userDetails.getUsername();

        // 유저 아이디로 생성된 토론방이 이미 있으면
        if (roomService.isExistRoom(AToken)) {
            return ResponseEntity.status(405).body(BaseResponseBody.of(405, "Session Already Exist"));
        }

        // 토론방 생성
        String code = roomService.createRoom(AToken, roomOpenReq);

        return ResponseEntity.status(200).body(StringRes.of(200, "Success", code));
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방 입장", notes = "토론 방 입장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterRoom(@ApiIgnore Authentication authentication, @RequestBody RoomEnterReq roomEnterReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String sessionID = roomService.decodeCode(roomEnterReq.getSessionId());

        UserInfoDto user;
        // 회원
        if (userDetails.isUser()) user = userService.getUserInfoDtoById(Long.parseLong(userDetails.getUsername()));
        // 비회원
        else user = new UserInfoDto(User.builder().nnm(userDetails.getNnm()).build());

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 비밀번호 오류
        if (!roomService.checkPwd(sessionID, roomEnterReq.getPwd()))
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong Password"));

        String token = roomService.enterRoom(sessionID, user);

        return ResponseEntity.status(200).body(VTokenRes.of(200, "Success", token));
    }

    @PutMapping("/enter/{sessionID}")
    @ApiOperation(value = "토큰 재발급", notes = "토론 방 토큰 재발급")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> reconnectRoom(@ApiIgnore Authentication authentication, @PathVariable String sessionID) {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();

        UserInfoDto user;
        // 회원
        if (userDetails.isUser()) user = userService.getUserInfoDtoById(Long.parseLong(userDetails.getUsername()));
        // 비회원
        else user = new UserInfoDto(User.builder().nnm(userDetails.getNnm()).build());

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

//        String token = roomService.enterRoom(roomEnterReq, user);
        String token = roomService.reconnect(sessionID, user.getId());
        if (token == null) return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));

        return ResponseEntity.status(200).body(VTokenRes.of(200, "Success", token));
    }

    @PostMapping("/session/{sessionID}")
    @ApiOperation(value = "세부 세션 생성", notes = "호스트의 세부 세션 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openSession(@ApiIgnore Authentication authentication, @PathVariable String sessionID) throws OpenViduJavaClientException, OpenViduHttpException, JsonProcessingException {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = userDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 호스트가 아니면
        if (!AToken.equals(sessionID)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Unauthorized"));

        Map<String, Map<String, String>> mapTokens = roomService.createSession(sessionID);

        String json = mapper.writeValueAsString(mapTokens);

        return ResponseEntity.status(200).body(JsonRes.of(200, "Success", json));
    }

    @DeleteMapping("/session/{sessionID}")
    @ApiOperation(value = "세부 세션 종료", notes = "호스트의 세부 세션 종료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeSession(@ApiIgnore Authentication authentication, @PathVariable String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = userDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 해당 세부 세션이 존재하지 않으면
        if (!roomService.isExistSession(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        // 호스트가 아니면
        if (!AToken.equals(sessionID)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        roomService.deleteSession(sessionID);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @PostMapping("/enter/select/{sessionID}")
    @ApiOperation(value = "진영 선택", notes = "원하는 진영에 들어갈 수 있는지 여부에 대한 응답")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 401, message = "권한 오류"),
            @ApiResponse(code = 403, message = "호출 할 수 있는 단계가 아님"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterPos(@ApiIgnore Authentication authentication, @PathVariable String sessionID, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 비회원이면
        if (!userService.isUser(AToken))
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

        String result = roomService.checkPos(sessionID, AToken, pos, true);
        switch (result) {
            case "400":
                return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));
            case "403":
                return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Cannot select except phase 0"));
            case "Success":
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
        }
    }

    @DeleteMapping("/enter/select/{sessionID}")
    @ApiOperation(value = "진영 나가기", notes = "해당 진영에서 나가기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 401, message = "권한 오류"),
            @ApiResponse(code = 403, message = "호출 할 수 있는 단계가 아님"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> leavePos(@ApiIgnore Authentication authentication, @PathVariable String sessionID, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 비회원이면
        if (!userService.isUser(AToken))
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

        String result = roomService.checkPos(AToken, sessionID, pos, false);
        switch (result) {
            case "400":
                return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));
            case "403":
                return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Cannot select except phase 0"));
            case "Success":
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            default:
                return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
        }
    }

    @GetMapping("/connections/agree")
    @ApiOperation(value = "패널 커넥션 ID 조회", notes = "토론방의 찬성 측 커넥션 이름 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAgreeConnections(String sessionID) throws JsonProcessingException {
        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        Map<String, String> connections = roomService.getAgreeConnections(sessionID);
        String json = mapper.writeValueAsString(connections);
        return ResponseEntity.status(200).body(JsonRes.of(200, "Success", json));
    }

    @GetMapping("/connections/disagree")
    @ApiOperation(value = "반대 측 커넥션 ID 조회", notes = "토론방의 찬성 측 커넥션 이름 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getDisagreeConnections(String sessionID) throws JsonProcessingException {
        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        Map<String, String> connections = roomService.getDisagreeConnections(sessionID);
        String json = mapper.writeValueAsString(connections);
        return ResponseEntity.status(200).body(JsonRes.of(200, "Success", connections.toString()));
    }

    @PutMapping("/start")
    @ApiOperation(value = "토론 시작", notes = "토론방을 모집중에서 시작으로 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "세션 없음"),
            @ApiResponse(code = 411, message = "시작 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> startRoom(@ApiIgnore Authentication authentication, @RequestBody String sessionID) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 요청한 사람이 호스트가 아니면
        if (!sessionID.equals(AToken)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        if (!roomService.updatePhaseBySessionID(sessionID, 1)) return ResponseEntity.status(411).body(BaseResponseBody.of(404, "Wrong status"));

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PutMapping("/cheer/{sessionID}/{pos}")
    @ApiOperation(value = "응원수 카운트", notes = "응원수 카운트 기능")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> updateCheerCnt(@ApiIgnore Authentication authentication, @PathVariable String sessionID, @PathVariable String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 해당 세션이 없으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session Not Exists"));

        // 해당 세션에 참가하지 않았으면
        if (!roomService.isParticipate(sessionID, AToken))
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not Participant"));

        String response = roomService.updateCheerCnt(sessionID, pos);

        if (response.equals("400"))
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Side parameter should be agree or disagree"));
        else if (response.equals("200")) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        else return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Error"));
    }

    @PostMapping("/vote/middle")
    @ApiOperation(value = "중간 투표", notes = "중간 투표 기능 ")
    public ResponseEntity<? extends BaseResponseBody> voteMiddle(Authentication authentication, String sessionID, String vote) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        //해당 토론방에 유저가 참가하지 않았다면
        if (!roomService.isParticipate(sessionID, AToken))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));

        String response = roomService.updateVoteMiddle(sessionID, AToken, vote);

        if (response.equals("400"))
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong parameter (vote)"));
        else if (response.equals("200")) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        else return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Error"));
    }

    @PutMapping("/vote")
    @ApiOperation(value = "투표 시작", notes = "토론 종료 전 토론 결과 투표")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "세션 없음"),
            @ApiResponse(code = 411, message = "시작 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> voteStart(@ApiIgnore Authentication authentication, @RequestBody String sessionID) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 요청한 사람이 호스트가 아니면
        if (!sessionID.equals(userId)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        // 해당 토론방이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        if (!roomService.updatePhaseBySessionID(sessionID, 2)) return ResponseEntity.status(411).body(BaseResponseBody.of(404, "Wrong status"));

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Vote start"));
    }

    @PostMapping("/vote/final")
    @ApiOperation(value = "최종 투표", notes = "최종 투표 기능 ")
    public ResponseEntity<? extends BaseResponseBody> voteFinal(@ApiIgnore Authentication authentication, String sessionID, String vote, String kingUserID) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!roomService.isExistRoom(sessionID))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        //해당 토론방에 유저가 참가하지 않았다면
        if (!roomService.isParticipate(sessionID, AToken))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));

        String response = roomService.updateVoteFinal(sessionID, AToken, vote, kingUserID);

        if (response.equals("400")) return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong parameter (vote)"));
        else if (response.equals("200")) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        else return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Error"));
    }

    @DeleteMapping()
    @ApiOperation(value = "종료", notes = "토론방 종료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "토론방 없음"),
            @ApiResponse(code = 411, message = "종료 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeRoom(@ApiIgnore Authentication authentication) throws OpenViduJavaClientException, OpenViduHttpException, JsonProcessingException {
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String AToken = userDetails.getUsername();

        if (!roomService.isExistRoom(AToken))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        if (!roomService.updatePhaseBySessionID(AToken, 3)) return ResponseEntity.status(411).body(BaseResponseBody.of(404, "Wrong status"));

        Map<String, String> result = roomService.finishRoom(AToken);
        String json = mapper.writeValueAsString(result);

        return ResponseEntity.status(200).body(JsonRes.of(200, "Success", json));
    }

}

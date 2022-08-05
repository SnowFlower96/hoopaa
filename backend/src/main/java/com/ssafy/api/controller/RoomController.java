package com.ssafy.api.controller;

import com.ssafy.api.request.RoomCloseReq;
import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.response.RoomRes;
import com.ssafy.api.response.StringRes;
import com.ssafy.api.response.VTokenRes;
import com.ssafy.api.service.RoomInfoService;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.data.UserInfo;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.common.data.VSession;
import com.ssafy.db.dto.UserInfoDto;
import com.ssafy.db.entity.RoomInfo;
import io.openvidu.java.client.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private OpenVidu openVidu;
    private Map<String, VSession> mapSessions = new ConcurrentHashMap<>();
    private String OPENVIDU_URL;
    private String SECRET;

    public RoomController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) throws OpenViduJavaClientException, OpenViduHttpException {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        this.openVidu.fetch();
        List<Session> sessionList = this.openVidu.getActiveSessions();

        for (Session session : sessionList) {
            session.close();
        }
    }

    @PostMapping
    @ApiOperation(value = "토론 방 생성", notes = "토론 방을 세팅하고 모집 중 상태로 만든다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 405, message = "이미 생성된 토론방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openRoom(@ApiIgnore Authentication authentication,
                                                               @RequestBody @ApiParam(value = "방 정보", required = true) RoomOpenReq openInfo) throws OpenViduJavaClientException, OpenViduHttpException {
        // Access Token 에서 유저 ID 추출
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = userDetails.getUsername();
        String userEm = userService.getUserById(Long.parseLong(userId)).getEm();

        // 유저 이메일로 생성된 세션이 이미 있으면
        if (this.mapSessions.get(userEm) != null) {
            return ResponseEntity.status(405).body(StringRes.of(405, "Session Already Exist"));
        }
        // 세션 생성
        else {
            Session session;
            // 세션 생성
            try {
                SessionProperties sessionProperties = new SessionProperties.Builder().customSessionId(userEm).build();
                session = this.openVidu.createSession(sessionProperties);
                this.mapSessions.put(userEm, new VSession());
                this.mapSessions.get(userEm).setSession(session);
            } catch (OpenViduJavaClientException | OpenViduHttpException e) {
                throw new RuntimeException(e);
            }
            // DB 저장
            try {
                RoomInfoDto roomInfoDto = roomService.createRoom(openInfo);
                this.mapSessions.get(userEm).setRoomInfo(roomInfoDto);
                this.mapSessions.get(userEm).setParticipants(new ConcurrentHashMap<>());
            } catch (Exception e) {
                // 생성된 세션 롤백
                session.close();
                this.mapSessions.remove(userEm);
                throw new RuntimeException(e);
            }

            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방 입장", notes = "토론 방 입장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterRoom(@ApiIgnore Authentication authentication, @RequestBody RoomEnterReq roomEnterReq) throws OpenViduJavaClientException, OpenViduHttpException {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String id = userDetails.getUsername();
        String userEm = userService.getUserInfoDtoById((Long.parseLong(id))).getEm();

        // 해당 세션이 존재하지 않으면
        if (!this.mapSessions.containsKey(roomEnterReq.getSessionId())) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        // 세션
        Connection connection;
        VSession vSession = this.mapSessions.get(roomEnterReq.getSessionId());
        Session session = vSession.getSession();

        // 세션 암호 확인
        String pwd = vSession.getRoomInfo().getPwd();
        if (pwd != null && !pwd.equals(roomEnterReq.getPwd())) return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong Password"));

        try {
            // Access Token의 유저와 세션의 ID가 같다면 Moderator 권한
            OpenViduRole role = userEm.equals(session.getSessionId()) ? OpenViduRole.MODERATOR : OpenViduRole.SUBSCRIBER;
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                    .role(role).build();
            connection = session.createConnection(connectionProperties);

            vSession.getParticipants().put(userEm, connection);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(200).body(VTokenRes.of(200, "Success", connection.getToken()));
    }

    @PostMapping("/enter/select")
    @ApiOperation(value = "진영 선택", notes = "원하는 진영에 들어갈 수 있는지 여부에 대한 응답")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterPos(@ApiIgnore Authentication authentication, String sessionId, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();
        UserInfoDto user = userService.getUserInfoDtoById(Long.parseLong(userId));

        // 해당 세션이 존재하지 않으면
        if (!this.mapSessions.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        // 세션
        VSession vSession = this.mapSessions.get(sessionId);
        int max = vSession.getRoomInfo().getMaxNum();
        UserInfo[] userInfos;

        // 유저가 선택하고자 하는 진영
        if (pos.equals("agree")) userInfos = vSession.getAgree();
        else if (pos.equals("disagree")) userInfos = vSession.getDisagree();
        else return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));

        // 참여할 수 있으면
        for (int i = 0; i < max; i++) {
            if (userInfos[i] == null) {
                userInfos[i] = new UserInfo(Long.parseLong(userId), user.getEm(), user.getNnm(), 0);
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            }
        }
        // 참여할 수 없으면
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Fail"));
    }

    @DeleteMapping("/enter/select")
    @ApiOperation(value = "진영 나가기", notes = "해당 진영에서 나가기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> leavePos(@ApiIgnore Authentication authentication, String sessionId, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!this.mapSessions.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        // 세션
        VSession vSession = this.mapSessions.get(sessionId);
        int max = vSession.getRoomInfo().getMaxNum();
        UserInfo[] userInfos;

        // 유저가 나가고자 하는 진영
        if (pos.equals("agree")) userInfos = vSession.getAgree();
        else if (pos.equals("disagree")) userInfos = vSession.getDisagree();
        else return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));

        // 나갈 수 있는지 확인
        for (int i = 0; i < max; i++) {
            // 나갈 수 있으면
            if (userInfos[i].getId() == Long.parseLong(userId)) {
                userInfos[i] = null;
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            }
        }
        // 나갈 수 없으면
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Fail"));
    }

    @PutMapping("/start/{room_id}")
    @ApiOperation(value = "토론 시작", notes = "토론방을 모집중에서 시작으로 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 411, message = "시작 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> startRoom(@RequestParam Long room_id){

        RoomInfoDto roomInfoDto = roomService.updatePhaseByRoomId(room_id, 1);
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

        RoomInfoDto roomInfoDto = roomService.updatePhaseByRoomId(room_id, 2);
        if(roomInfoDto == null){
            return ResponseEntity.status(200).body(RoomRes.of(411, "Voting has already started or ended"));
        }
        return ResponseEntity.status(200).body(RoomRes.of(200, "vote start"));

    }

    @DeleteMapping()
    @ApiOperation(value = "종료", notes = "세션 종료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 411, message = "종료 불가"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeRoom(@ApiIgnore Authentication authentication) throws OpenViduJavaClientException, OpenViduHttpException {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String id = userDetails.getUsername();
        String userEm = userService.getUserById(Long.parseLong(id)).getEm();

        Session session = this.mapSessions.get(userEm).getSession();
        session.close();

        return ResponseEntity.status(200).body(RoomRes.of(200, "Success"));

    }

}

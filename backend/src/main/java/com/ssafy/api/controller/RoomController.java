package com.ssafy.api.controller;

import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.api.request.SessionReq;
import com.ssafy.api.response.JsonRes;
import com.ssafy.api.response.StringRes;
import com.ssafy.api.response.VTokenRes;
import com.ssafy.api.response.VTokensRes;
import com.ssafy.api.service.RoomInfoService;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.data.UserInfo;
import com.ssafy.common.data.VSession;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.common.data.VRoom;
import com.ssafy.db.dto.UserInfoDto;
import com.ssafy.db.entity.User;
import io.openvidu.java.client.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
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
    private Map<String, VRoom> mapRooms = new ConcurrentHashMap<>();
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

        // 유저 아이디로 생성된 세션이 이미 있으면
        if (this.mapRooms.get(userId) != null) {
            System.out.println("Duple");
            return ResponseEntity.status(405).body(StringRes.of(405, "Session Already Exist"));
        }
        // 세션 생성
        else {
            Session session;
            // 세션 생성
            try {
                SessionProperties sessionProperties = new SessionProperties.Builder().customSessionId(userId).build();
                session = this.openVidu.createSession(sessionProperties);
                this.mapRooms.put(userId, new VRoom());
                this.mapRooms.get(userId).setSession(session);
                this.mapRooms.get(userId).setAgree(new UserInfo[openInfo.getMax_num()]);
                this.mapRooms.get(userId).setDisagree(new UserInfo[openInfo.getMax_num()]);
                this.mapRooms.get(userId).setMapParticipants(new ConcurrentHashMap<>());
            } catch (OpenViduJavaClientException | OpenViduHttpException e) {
                throw new RuntimeException(e);
            }
            // DB 저장
            try {
                RoomInfoDto roomInfoDto = roomService.createRoom(openInfo);
                this.mapRooms.get(userId).setRoomInfo(roomInfoDto);
                this.mapRooms.get(userId).setMapConnections(new ConcurrentHashMap<>());
            } catch (Exception e) {
                // 생성된 세션 롤백
                session.close();
                this.mapRooms.remove(userId);
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
        UserInfoDto user = null;
        try {
            user = userService.getUserInfoDtoById(Long.parseLong(id));
        } catch (Exception e) {
            log.debug("Non User");
        }

        if (user == null) user = new UserInfoDto(User.builder().nnm(id).build());

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(roomEnterReq.getSessionId())) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 세션
        Connection connection;
        VRoom vRoom = this.mapRooms.get(roomEnterReq.getSessionId());
        Session session = vRoom.getSession();

        // 세션 암호 확인
        String pwd = vRoom.getRoomInfo().getPwd();
        if (pwd != null && !pwd.equals(roomEnterReq.getPwd())) return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong Password"));

        try {
            // Access Token의 유저와 세션의 ID가 같다면 Moderator 권한
            OpenViduRole role = id.equals(session.getSessionId()) ? OpenViduRole.MODERATOR : OpenViduRole.SUBSCRIBER;
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                    .role(role).build();
            connection = session.createConnection(connectionProperties);

            vRoom.getMapConnections().put(id, connection);

            //토론방 참가자 관리 Map에 저장
            vRoom.getMapParticipants().put(id, new UserInfo(id, user.getEm(), user.getNnm(), 0, false, false, false));
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            vRoom.getMapConnections().remove(id);
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(200).body(VTokenRes.of(200, "Success", connection.getToken()));
    }

    @PostMapping("/session")
    @ApiOperation(value = "세부 세션 생성", notes = "호스트의 세부 세션 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> openSession(@ApiIgnore Authentication authentication, @RequestBody SessionReq sessionReq) throws OpenViduJavaClientException, OpenViduHttpException {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String id = userDetails.getUsername();
        String sessionID = sessionReq.getSessionId();

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionReq.getSessionId())) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 호스트가 아니면
        if (!id.equals(sessionID)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Unauthorized"));

        // 세부 세션 생성
        SessionProperties sessionProperties;
        Session sessionAgree;
        Session sessionDisagree;
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(id + "_agree").build();
            sessionAgree = this.openVidu.createSession(sessionProperties);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(id + "_disagree").build();
            sessionDisagree = this.openVidu.createSession(sessionProperties);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            // 생성된 찬성 세부세션 롤백
            assert sessionAgree != null;
            sessionAgree.close();
            throw new RuntimeException(e);
        }

        // mapSessions 에 정보 추가
        VSession vSession = VSession.builder()
                .sessionAgree(sessionAgree).sessionDisagree(sessionDisagree)
                .mapAgree(new ConcurrentHashMap<>()).mapDisagree(new ConcurrentHashMap<>())
                .build();
        mapSessions.put(sessionID, vSession);

        // 세부 세션 커넥션 생성 및 토큰 반환
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER).build();;
        Connection connection;
        // 찬성측
        for (String userId : sessionReq.getAgree()) {
            try {
                connection = sessionAgree.createConnection(connectionProperties);
                mapSessions.get(sessionID).getMapAgree().put(userId, connection);
            } catch (OpenViduJavaClientException | OpenViduHttpException e) {
                mapSessions.remove(sessionID);
                throw new RuntimeException(e);
            }
        }
        // 반대측
        for (String userId : sessionReq.getDisagree()) {
            try {
                connection = sessionDisagree.createConnection(connectionProperties);
                mapSessions.get(sessionID).getMapDisagree().put(userId, connection);
            } catch (OpenViduJavaClientException | OpenViduHttpException e) {
                mapSessions.remove(sessionID);
                throw new RuntimeException(e);
            }
        }

        // 각 유저별로 토큰을 반환하기 위한 객체 생성
        Map<String, String> mapTokens = new ConcurrentHashMap<>();
        // 찬성측
        for (String em : mapSessions.get(sessionID).getMapAgree().keySet()) {
            mapTokens.put(em, mapSessions.get(sessionID).getMapAgree().get(em).getToken());
        }
        // 반대측
        for (String em : mapSessions.get(sessionID).getMapDisagree().keySet()) {
            mapTokens.put(em, mapSessions.get(sessionID).getMapDisagree().get(em).getToken());
        }

        return ResponseEntity.status(200).body(VTokensRes.of(200, "Success", mapTokens));
    }

    @DeleteMapping("/session")
    @ApiOperation(value = "세부 세션 종료", notes = "호스트의 세부 세션 종료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 403, message = "권한 오류"),
            @ApiResponse(code = 404, message = "없는 토론 방"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> closeSession(@ApiIgnore Authentication authentication, @RequestBody SessionReq sessionReq) throws OpenViduJavaClientException, OpenViduHttpException {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String id = userDetails.getUsername();
        String sessionID = sessionReq.getSessionId();

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionReq.getSessionId())) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 해당 세부 세션이 존재하지 않으면
        if (!this.mapSessions.containsKey(sessionReq.getSessionId())) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        // 호스트가 아니면
        if (!id.equals(sessionID)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        // 세션 종료
        this.mapSessions.get(sessionID).getSessionAgree().close();
        this.mapSessions.get(sessionID).getSessionDisagree().close();

        // 세션 정보 삭제
        this.mapSessions.remove(sessionID);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


    @PostMapping("/enter/select")
    @ApiOperation(value = "진영 선택", notes = "원하는 진영에 들어갈 수 있는지 여부에 대한 응답")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 403, message = "호출 할 수 있는 단계가 아님"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterPos(@ApiIgnore Authentication authentication, String sessionId, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 세션
        VRoom vRoom = this.mapRooms.get(sessionId);
        // 모집중 단계가 아닐 시
        if (vRoom.getRoomInfo().getPhase() != 0) return ResponseEntity.status(405).body(BaseResponseBody.of(405, "Cannot select except phase 0"));

        int max = vRoom.getRoomInfo().getMaxNum();
        UserInfo[] userInfos;

        // 유저가 선택하고자 하는 진영
        if (pos.equals("agree")) userInfos = vRoom.getAgree();
        else if (pos.equals("disagree")) userInfos = vRoom.getDisagree();
        else return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));

        // 참여할 수 있으면
        for (int i = 0; i < max; i++) {
            if (userInfos[i] == null) {
                userInfos[i] = vRoom.getMapParticipants().get(userId);
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
            @ApiResponse(code = 403, message = "호출 할 수 있는 단계가 아님"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> leavePos(@ApiIgnore Authentication authentication, String sessionId, String pos) {
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        // 세션
        VRoom vRoom = this.mapRooms.get(sessionId);
        // 모집중 단계가 아닐 시
        if (vRoom.getRoomInfo().getPhase() != 0) return ResponseEntity.status(405).body(BaseResponseBody.of(405, "Cannot select except phase 0"));

        int max = vRoom.getRoomInfo().getMaxNum();
        UserInfo[] userInfos;

        // 유저가 나가고자 하는 진영
        if (pos.equals("agree")) userInfos = vRoom.getAgree();
        else if (pos.equals("disagree")) userInfos = vRoom.getDisagree();
        else return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Wrong request"));

        // 나갈 수 있는지 확인
        for (int i = 0; i < max; i++) {
            // 나갈 수 있으면
            if (userInfos[i].getId().equals(userId)) {
                userInfos[i] = null;
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
            }
        }
        // 나갈 수 없으면
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Fail"));
    }

    @GetMapping("/connections/agree")
    @ApiOperation(value = "찬성 측 커넥션 ID 조회", notes = "토론방의 찬성 측 커넥션 이름 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAgreeConnections(String sessionID) {
        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionID)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        VRoom vRoom = this.mapRooms.get(sessionID);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (UserInfo userInfo : vRoom.getAgree()) {
            if (vRoom.getMapConnections().containsKey(userInfo.getId())){
                Connection conn = vRoom.getMapConnections().get(userInfo.getId());
                connections.put(userInfo.getId(), conn.getConnectionId());
            }
        }

        return ResponseEntity.status(200).body(JsonRes.of(200, "Success", connections.toString()));
    }

    @GetMapping("/connections/disagree")
    @ApiOperation(value = "반대 측 커넥션 ID 조회", notes = "토론방의 찬성 측 커넥션 이름 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "파라미터 오류"),
            @ApiResponse(code = 404, message = "해당 세션 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getDisagreeConnections(String sessionId) {
        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        VRoom vRoom = this.mapRooms.get(sessionId);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (UserInfo userInfo : vRoom.getDisagree()) {
            if (vRoom.getMapConnections().containsKey(userInfo.getEm())){
                Connection conn = vRoom.getMapConnections().get(userInfo.getEm());
                connections.put(userInfo.getEm(), conn.getConnectionId());
            }
        }

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
    public ResponseEntity<? extends BaseResponseBody> startRoom(@ApiIgnore Authentication authentication, @RequestBody String sessionId){
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 요청한 사람이 호스트가 아니면
        if (!sessionId.equals(userId)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        VRoom vRoom = this.mapRooms.get(sessionId);
        vRoom.getRoomInfo().setPhase(1);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Debate start"));
    }

    @PostMapping("/vote/middle")
    @ApiOperation(value = "중간 투표", notes = "중간 투표 기능 ")
    public ResponseEntity<? extends BaseResponseBody> voteMiddle(Authentication authentication, String sessionId, String vote){
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        VRoom vRoom= this.mapRooms.get(sessionId);
        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        //해당 토론방에 유저가 참가하지 않았다면
        if(!vRoom.getMapParticipants().containsKey(userId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));

        UserInfo userInfo = vRoom.getMapParticipants().get(userId);

        if(vote.equals("agree")){
            if(userInfo.isHasDisagree()){ //기존에 반대였다면
                userInfo.setHasDisagree(false);
                //반대표 1 감소
                vRoom.setVote_disagree(vRoom.getVote_disagree()-1);
            }
            //이미 찬성한 상태가 아니라면
            if(!userInfo.isHasAgree()) {
                //참가자의 중간 투표 현황 업데이트
                userInfo.setHasAgree(true);
                //찬성표 1 증가
                vRoom.setVote_agree(vRoom.getVote_agree() + 1);
            }
        }else if(vote.equals("disagree")){
            if(userInfo.isHasAgree()){ //기존에 찬성이었다면
                userInfo.setHasAgree(false);
                //찬성표 1 감소
                vRoom.setVote_agree(vRoom.getVote_agree()-1);
            }
            //이미 찬성한 상태가 아니라면
            if(!userInfo.isHasDisagree()) {
                //참가자의 중간 투표 현황 업데이트
                userInfo.setHasDisagree(true);
                //반대표 1 증가
                vRoom.setVote_disagree(vRoom.getVote_disagree() + 1);
            }
        }

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
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
    public ResponseEntity<? extends BaseResponseBody> voteStart(@ApiIgnore Authentication authentication, @RequestBody String sessionId){
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        // 요청한 사람이 호스트가 아니면
        if (!sessionId.equals(userId)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Not host"));

        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Room not exists"));

        VRoom vRoom = this.mapRooms.get(sessionId);
        vRoom.getRoomInfo().setPhase(2);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Vote start"));
    }

    @PostMapping("/vote/final")
    @ApiOperation(value = "최종 투표", notes = "최종 투표 기능 ")
    public ResponseEntity<? extends BaseResponseBody> voteFinal(Authentication authentication, String sessionId, String vote, String kingUserEm){
        SsafyUserDetails ssafyUserDetails = (SsafyUserDetails) authentication.getDetails();
        String userId = ssafyUserDetails.getUsername();

        VRoom vRoom= this.mapRooms.get(sessionId);
        // 해당 세션이 존재하지 않으면
        if (!this.mapRooms.containsKey(sessionId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "Session not exists"));

        //해당 토론방에 유저가 참가하지 않았다면
        if(!vRoom.getMapParticipants().containsKey(userId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));

        UserInfo userInfo = vRoom.getMapParticipants().get(userId);

        //아직 마지막 투표를 하지 않았다면
        if(!userInfo.isHasFinalVote()){
            userInfo.setHasFinalVote(true);
            if(vote.equals("agree")){
                vRoom.setVote_final_agree(vRoom.getVote_final_agree()+1);
            }else{
                vRoom.setVote_final_disagree(vRoom.getVote_final_disagree()+1);
            }
        }

        //토론왕 업데이트
//        if(!vRoom.getMapParticipants().containsKey(kingUserEm)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));
        //토론왕 투표 받은 유저
        UserInfo kingUser = vRoom.getMapParticipants().get(kingUserEm);
        //토론왕 득표 수 업데이트
        kingUser.setKingCnt(kingUser.getKingCnt()+1);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
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

        VRoom vRoom = this.mapRooms.get(id);
        Session session = vRoom.getSession();
        session.close();
        roomService.finishRoom(vRoom);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/sync")
    @ApiOperation(value = "동기화", notes = "OpenVidu 서버와의 동기화")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류류")
   })
    public ResponseEntity<? extends BaseResponseBody> syncServer() {
        // TODO
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @PostMapping("/init")
    @ApiOperation(value = "초기화")
    public ResponseEntity<? extends BaseResponseBody> initServer() throws OpenViduJavaClientException, OpenViduHttpException {
        // 정보 초기화
        mapRooms = new ConcurrentHashMap<>();
        mapSessions = new ConcurrentHashMap<>();
        // openvidu 초기화
        this.openVidu.fetch();

        List<Session> list = this.openVidu.getActiveSessions();
        for (Session s : list) s.close();
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

}

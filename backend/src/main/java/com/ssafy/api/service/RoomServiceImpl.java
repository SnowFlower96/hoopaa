package com.ssafy.api.service;

import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.common.data.VRoom;
import com.ssafy.common.data.VSession;
import com.ssafy.common.data.VUserInfo;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.dto.UserInfoDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl implements RoomService {

    private Map<String, VRoom> mapRooms;
    private Map<String, VSession> mapSessions;

    private OpenVidu openVidu;
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    @Value("${openvidu.secret}")
    private String SECRET;

    @PostConstruct
    void init() throws OpenViduJavaClientException, OpenViduHttpException {
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        this.openVidu.fetch();
        List<Session> sessionList = this.openVidu.getActiveSessions();

        for (Session session : sessionList) {
            session.close();
        }

        this.mapRooms = new ConcurrentHashMap<>();
        this.mapSessions = new ConcurrentHashMap<>();
    }

    @Autowired
    RoomInfoRepository roomInfoRepository;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    RoomService roomService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomPanelRepository roomPanelRepository;

    @Autowired
    RoomDescriptionRepository roomDescriptionRepository;

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Autowired
    UserStatRepository userStatRepository;

    @Override
    public void createRoom(String sessionID, RoomOpenReq roomOpenReq) {
        // 세션 생성
        Session session;
        try {
            SessionProperties sessionProperties = new SessionProperties.Builder().customSessionId(sessionID).build();
            session = this.openVidu.createSession(sessionProperties);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }

        // DB 저장
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(roomOpenReq.getPwd())
                .hostId(Long.parseLong(sessionID))
                .maxNum(roomOpenReq.getMax_num())
                .build();
        roomInfo = roomInfoRepository.save(roomInfo);

        RoomPanel roomPanel = RoomPanel.builder()
                .id(roomInfo.getId())
                .build();
        RoomDescription roomDescription = RoomDescription.builder()
                .id(roomInfo.getId())
                .title(roomOpenReq.getTitle())
                .subtitle(roomOpenReq.getSubtitle())
                .cate(roomOpenReq.getCate())
                .build();
        roomPanelRepository.save(roomPanel);
        roomDescriptionRepository.save(roomDescription);
        // 해시태그 검색
        // TODO StringTokenizer("#") 사용

        // 서버에 저장
        RoomInfoDto roomInfoDto = RoomInfoDto.builder()
                .id(roomInfo.getId()).pwd(roomInfo.getPwd())
                .thumbUrl(roomInfo.getThumbUrl()).phase(roomInfo.getPhase())
                .maxNum(roomInfo.getMaxNum()).curNum(roomInfo.getCurNum())
                .startTime(roomInfo.getStartTime())
                .cate(roomOpenReq.getCate())
                .title(roomOpenReq.getTitle()).subtitle(roomOpenReq.getSubtitle())
                .build();
        VRoom vRoom = VRoom.builder().session(session)
                .mapConnections(new ConcurrentHashMap<>()).mapParticipants(new ConcurrentHashMap<>())
                .roomInfoDto(roomInfoDto)
                .agree(new VUserInfo[roomOpenReq.getMax_num()]).disagree(new VUserInfo[roomOpenReq.getMax_num()])
                .build();
        this.mapRooms.put(sessionID, vRoom);
    }

    @Override
    public boolean isExistRoom(String sessionID) {
        return this.mapRooms.containsKey(sessionID);
    }

    @Override
    public boolean isExistSession(String sessionID) {
        return this.mapSessions.containsKey(sessionID);
    }

    @Override
    public boolean isParticipate(String sessionID, String userID) {
        return this.mapRooms.get(sessionID).getMapParticipants().containsKey(userID);
    }

    @Override
    public void deleteRoom(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        this.mapRooms.get(sessionID).getSession().close();
        this.mapRooms.remove(sessionID);
        this.mapSessions.remove(sessionID);
    }

    @Override
    public boolean checkPwd(String sessionID, String pwd) {
        return pwd.equals(this.mapRooms.get(sessionID).getRoomInfoDto().getPwd());
    }

    @Override
    public String enterRoom(RoomEnterReq roomEnterReq, UserInfoDto userInfoDto) {
        Connection connection;
        VRoom vRoom = this.mapRooms.get(roomEnterReq.getSessionId());
        Session session = vRoom.getSession();

        try {
            // 권한 설정
            // 유저의 ID와 세션의 ID가 같다면 Moderator 권한
            OpenViduRole role;
            if (userInfoDto.getId().equals(session.getSessionId())) role = OpenViduRole.MODERATOR;
            else {  // 회원이지만 호스트가 아니거나 비회원인 경우
                if (userInfoDto.getId() != null) role = OpenViduRole.PUBLISHER;  // 회원
                else role = OpenViduRole.SUBSCRIBER;  // 비회원
            }

            // Connection 생성
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                    .role(role).build();
            connection = session.createConnection(connectionProperties);

            // 방 정보에 connection 추가
            vRoom.getMapConnections().put(userInfoDto.getId(), connection);

            // 방 정보에 UserInfo 추가

            VUserInfo vUserInfo = VUserInfo.builder()
                    .id(userInfoDto.getId()).em(userInfoDto.getEm()).em(userInfoDto.getEm())
                    .build();
            vRoom.getMapParticipants().put(userInfoDto.getId(), vUserInfo);

            return connection.getToken();
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            // 롤백
            vRoom.getMapConnections().remove(userInfoDto.getId());
            vRoom.getMapParticipants().remove(userInfoDto.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Map<String, String>> createSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        // 세부 세션 생성 및 커넥션 변수 선언
        SessionProperties sessionProperties;
        VRoom vRoom = this.mapRooms.get(sessionID);
        Session sessionAgree;
        Session sessionDisagree;
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER).build();
        Connection connection;
        Map<String, Connection> mapAgree = new ConcurrentHashMap<>();
        Map<String, Connection> mapDisagree = new ConcurrentHashMap<>();

        // 찬성 세션 생성 및 토큰 생성
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(sessionID + "_agree").build();
            sessionAgree = this.openVidu.createSession(sessionProperties);
            for (VUserInfo VUserInfo : vRoom.getAgree()) {
                if (VUserInfo == null) continue;
                connection = sessionAgree.createConnection(connectionProperties);
                mapAgree.put(VUserInfo.getId(), connection);
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
        // 반대 세션 생성 및 토큰 생성
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(sessionID + "_disagree").build();
            sessionDisagree = this.openVidu.createSession(sessionProperties);
            for (VUserInfo VUserInfo : vRoom.getDisagree()) {
                if (VUserInfo == null) continue;
                connection = sessionDisagree.createConnection(connectionProperties);
                mapDisagree.put(VUserInfo.getId(), connection);
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            // 생성된 찬성 세부세션 롤백
            assert sessionAgree != null;
            sessionAgree.close();
            throw new RuntimeException(e);
        }

        // mapSessions 에 정보 추가
        VSession vSession = VSession.builder()
                .sessionAgree(sessionAgree).sessionDisagree(sessionDisagree)
                .mapAgree(mapAgree).mapDisagree(mapDisagree)
                .build();
        mapSessions.put(sessionID, vSession);

        // 각 유저별로 토큰을 반환하기 위한 객체 생성
        Map<String, Map<String, String>> mapTokens = new ConcurrentHashMap<>();
        // 찬성측
        for (String id : mapAgree.keySet()) {
            Map<String, String> map = new HashMap<>();
            map.put("connectionID", mapAgree.get(id).getConnectionId());
            map.put("token", mapAgree.get(id).getToken());
            mapTokens.put(id, map);
        }
        // 반대측
        for (String id : mapDisagree.keySet()) {
            Map<String, String> map = new HashMap<>();
            map.put("connectionID", mapDisagree.get(id).getConnectionId());
            map.put("token", mapDisagree.get(id).getToken());
            mapTokens.put(id, map);
        }
        return mapTokens;
    }

    @Override
    public void deleteSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        // 세션 종료
        this.mapSessions.get(sessionID).getSessionAgree().close();
        this.mapSessions.get(sessionID).getSessionDisagree().close();

        // 세션 정보 삭제
        this.mapSessions.remove(sessionID);
    }

    @Override
    public String checkPos(String sessionID, String AToken, String pos, boolean isSelect) {
        // 세션
        VRoom vRoom = this.mapRooms.get(sessionID);

        // 모집중 단계가 아닐 시
        if (vRoom.getRoomInfoDto().getPhase() != 0) return "403";

        int max = vRoom.getRoomInfoDto().getMaxNum();
        VUserInfo[] VUserInfos;

        // 유저가 선택하고자 하는 진영
        if (pos.equals("agree")) VUserInfos = vRoom.getAgree();
        else if (pos.equals("disagree")) VUserInfos = vRoom.getDisagree();
        else return "400";

        // 참여할 수 있으면
        System.out.println("In checkPos : Length = " + VUserInfos.length);
        for (int i = 0; i < max; i++) {
            System.out.println("In checkPos : " + VUserInfos[i]);
            if (VUserInfos[i] == null) {
                VUserInfos[i] = isSelect ? vRoom.getMapParticipants().get(AToken) : null;
                return "Success";
            }
        }
        // 참여할 수 없으면
        return "Fail";
    }

    @Override
    public Map<String, String> getAgreeConnections(String sessionID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        for (String key : vRoom.getMapConnections().keySet()) System.out.println(key);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (VUserInfo VUserInfo : vRoom.getAgree()) {
            System.out.println(VUserInfo);
            if (vRoom.getMapConnections().containsKey(VUserInfo.getId())) {
                Connection conn = vRoom.getMapConnections().get(VUserInfo.getId());
                connections.put(VUserInfo.getId(), conn.getConnectionId());
            }
        }

        return connections;
    }

    @Override
    public Map<String, String> getDisagreeConnections(String sessionID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (VUserInfo VUserInfo : vRoom.getDisagree()) {
            if (vRoom.getMapConnections().containsKey(VUserInfo.getEm())) {
                Connection conn = vRoom.getMapConnections().get(VUserInfo.getEm());
                connections.put(VUserInfo.getEm(), conn.getConnectionId());
            }
        }

        return connections;
    }

    @Override
    public Boolean updatePhaseBySessionID(String sessionID, int phase) {
        RoomInfoDto roomInfoDto = mapRooms.get(sessionID).getRoomInfoDto();

        if (roomInfoDto.getPhase() >= phase || phase < 0 || phase > 2) return false;

        // 세션 변경
        roomInfoDto.setPhase(phase);

        // DB 변경
        RoomInfo roomInfo = roomInfoRepository.findRoomInfoById(roomInfoDto.getId()).get();
        roomInfo.setPhase(phase);
        roomInfoRepository.save(roomInfo);

        return true;
    }

    @Override
    public String updateCheerCnt(String sessionID, String pos) {
        if (pos.equals("agree"))
            this.mapRooms.get(sessionID).setCheer_agree(this.mapRooms.get(sessionID).getCheer_agree() + 1);
        else if (pos.equals("disagree"))
            this.mapRooms.get(sessionID).setCheer_agree(this.mapRooms.get(sessionID).getCheer_agree() + 1);
        else return "400";

        return "200";
    }

    @Override
    public String updateVoteMiddle(String sessionID, String userID, String vote) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        VUserInfo VUserInfo = vRoom.getMapParticipants().get(userID);

        if (vote.equals("agree")) {
            if (VUserInfo.isHasDisagree()) { //기존에 반대였다면
                VUserInfo.setHasDisagree(false);
                //반대표 1 감소
                vRoom.setVote_disagree(vRoom.getVote_disagree() - 1);
            }
            //이미 찬성한 상태가 아니라면
            if (!VUserInfo.isHasAgree()) {
                //참가자의 중간 투표 현황 업데이트
                VUserInfo.setHasAgree(true);
                //찬성표 1 증가
                vRoom.setVote_agree(vRoom.getVote_agree() + 1);
            }
        } else if (vote.equals("disagree")) {
            if (VUserInfo.isHasAgree()) { //기존에 찬성이었다면
                VUserInfo.setHasAgree(false);
                //찬성표 1 감소
                vRoom.setVote_agree(vRoom.getVote_agree() - 1);
            }
            //이미 찬성한 상태가 아니라면
            if (!VUserInfo.isHasDisagree()) {
                //참가자의 중간 투표 현황 업데이트
                VUserInfo.setHasDisagree(true);
                //반대표 1 증가
                vRoom.setVote_disagree(vRoom.getVote_disagree() + 1);
            }
        } else
            return "400";

        return "200";
    }

    @Override
    public String updateVoteFinal(String sessionID, String userID, String vote, String kingUserID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        VUserInfo VUserInfo = vRoom.getMapParticipants().get(userID);

        //아직 마지막 투표를 하지 않았다면
        if (!VUserInfo.isHasFinalVote()) {
            VUserInfo.setHasFinalVote(true);
            if (vote.equals("agree")) {
                vRoom.setVote_final_agree(vRoom.getVote_final_agree() + 1);
            } else if (vote.equals("disagree")) {
                vRoom.setVote_final_disagree(vRoom.getVote_final_disagree() + 1);
            } else return "400";
        }

        //토론왕 업데이트
//        if(!vRoom.getMapParticipants().containsKey(kingUserEm)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));
        //토론왕 투표 받은 유저
        VUserInfo kingUser = vRoom.getMapParticipants().get(kingUserID);
        //토론왕 득표 수 업데이트
        kingUser.setKingCnt(kingUser.getKingCnt() + 1);

        return "200";
    }

    @Override
    @Transactional
    public Map<String, String> finishRoom(String userID) {
        // 해당 유저가 만든 토론방의 VRoom 객체
        VRoom vRoom = this.mapRooms.get(userID);

        this.mapSessions.remove(userID);  // 세부세션 제거

        // DB에 저장
        try {
            RoomInfo roomInfo = roomInfoRepository.findById(vRoom.getRoomInfoDto().getId()).get();

            // 승패 설정
            short winner = -1;  // 무승부
            if (vRoom.getVote_final_agree() > vRoom.getVote_final_disagree()) winner = 1;
            else if (vRoom.getVote_final_agree() < vRoom.getVote_final_disagree()) winner = 2;
            // 토론왕 확인
            int kingCntMax = -1;
            String kingID = null;
            for (int i = 0; i < vRoom.getRoomInfoDto().getMaxNum(); i++) {
                VUserInfo vUserInfo = vRoom.getAgree()[i];
                if (vUserInfo != null && vUserInfo.getKingCnt() > kingCntMax) {
                    kingCntMax = vUserInfo.getKingCnt();
                    kingID = vUserInfo.getId();
                }
                vUserInfo = vRoom.getDisagree()[i];
                if (vUserInfo != null && vUserInfo.getKingCnt() > kingCntMax) {
                    kingCntMax = vUserInfo.getKingCnt();
                    kingID = vUserInfo.getId();
                }
            }

            // room_info 업데이트
            roomInfo.setWinner(winner);
            roomInfo.setAgree(roomInfo.getAgree());
            roomInfo.setDisagree(roomInfo.getDisagree());
            roomInfo.setKingId(kingID != null ? Long.parseLong(kingID) : null);

            // user history 업데이트
            // 찬성 진영 업데이트
            for (VUserInfo VUserInfo : vRoom.getAgree()) {
                if (VUserInfo == null) continue;
                boolean isHost = Objects.equals(roomInfo.getHostId(), Long.parseLong(VUserInfo.getId()));
                boolean isKing = false;

                User user = userRepository.findUserById(Long.parseLong(VUserInfo.getId())).get();
                UserHistory userHistory = UserHistory.builder()
                        .userId(user.getId())
                        .roomId(roomInfo.getId())
                        .isHost(isHost)
                        .isKing(isKing)
                        .userPos(1)
                        .build();
                userHistoryRepository.save(userHistory);

                //user stat 업데이트
                UserStat userStat = userStatRepository.findStatById(user.getId()).get();
                userStat.setTotal(userStat.getTotal() + 1);
                //승 , 패 , 무
                if (winner == 1) {
                    userStat.setWin(userStat.getWin() + 1);
                } else if (winner == 2) {
                    userStat.setLose(userStat.getLose() + 1);
                } else {
                    userStat.setDraw(userStat.getDraw() + 1);
                }
                userStatRepository.save(userStat);
            }

            //반대 진영 업데이트
            for (VUserInfo VUserInfo : vRoom.getDisagree()) {
                if (VUserInfo == null) continue;
                boolean isHost = Objects.equals(roomInfo.getHostId(), Long.parseLong(VUserInfo.getId()));
                boolean isKing = false;

                User user = userRepository.findUserById(Long.parseLong(VUserInfo.getId())).get();
                UserHistory userHistory = UserHistory.builder()
                        .userId(user.getId())
                        .roomId(roomInfo.getId())
                        .isHost(isHost)
                        .isKing(isKing)
                        .userPos(1)
                        .build();
                userHistoryRepository.save(userHistory);

                //user stat 업데이트
                UserStat userStat = userStatRepository.findStatById(user.getId()).get();
                userStat.setTotal(userStat.getTotal() + 1);
                //승 , 패 , 무
                if (winner == 1) {
                    userStat.setWin(userStat.getWin() + 1);
                } else if (winner == 2) {
                    userStat.setLose(userStat.getLose() + 1);
                } else {
                    userStat.setDraw(userStat.getDraw() + 1);
                }
                // 토론왕
                if (isHost) userStat.setKing(userStat.getKing() + 1);
                userStatRepository.save(userStat);
            }
            // openVidu 세션 종료
            Session session = vRoom.getSession();
            session.close();

            Map<String, String> result = new HashMap<>();
            result.put("agree", String.valueOf(vRoom.getVote_final_agree()));
            result.put("disagree", String.valueOf(vRoom.getVote_final_disagree()));
            result.put("king", kingID);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findHashtagId(String nm) {
        hashtagRepository.findHashtagByNm(nm);

        Optional<RoomHashtag> hash = hashtagRepository.findHashtagByNm(nm);
        if (hash.isPresent()) { //이미 존재 한다면
            RoomHashtag roomHashtag = hash.get();
            roomHashtag.setCnt(roomHashtag.getCnt() + 1);
            hashtagRepository.save(roomHashtag);
            return roomHashtag.getId();
        }
        //존재하지 않던 해시태그라면
        RoomHashtag roomHashtag = RoomHashtag.builder()
                .nm(nm)
                .cnt(0L)
                .build();
        hashtagRepository.save(roomHashtag);

        return findHashtagId(roomHashtag.getNm());
    }

    @Override
    public void syncServer() {

    }

    @Override
    public void initServer() throws OpenViduJavaClientException, OpenViduHttpException {
        // 서버 초기화
        this.openVidu.fetch();
        List<Session> list = this.openVidu.getActiveSessions();
        for (Session s : list) s.close();

        // 정보 초기화
        mapRooms.clear();
        mapSessions.clear();
    }
}

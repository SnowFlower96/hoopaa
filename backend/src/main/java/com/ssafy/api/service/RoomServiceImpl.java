package com.ssafy.api.service;

import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.common.util.AES128Util;
import com.ssafy.common.vidu.ConnectionDto;
import com.ssafy.common.vidu.VRoom;
import com.ssafy.common.vidu.VSession;
import com.ssafy.common.vidu.VUserInfo;
import com.ssafy.db.dto.RoomInfoDto;
import com.ssafy.db.dto.UserInfoDto;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private Map<String, VRoom> mapRooms;

    private OpenVidu openVidu;
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    @Value("${openvidu.secret}")
    private String SECRET;
    @Value("${upload.path}")
    private String thumbPath;

    private AES128Util aes128Util;
    @Value("${aes.key}")
    private String key;

    @PostConstruct
    void init() throws OpenViduJavaClientException, OpenViduHttpException {
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        this.openVidu.fetch();
        List<Session> sessionList = this.openVidu.getActiveSessions();

        for (Session session : sessionList) {
            session.close();
        }

        this.mapRooms = new ConcurrentHashMap<>();
        aes128Util = new AES128Util(key);
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
    public String createRoom(String sessionID, RoomOpenReq roomOpenReq) throws IOException {
        // ?????? ??????
        Session session;
        try {
            SessionProperties sessionProperties = new SessionProperties.Builder().customSessionId(sessionID).build();
            session = this.openVidu.createSession(sessionProperties);
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }

        // DB ??????
        // ????????????
        StringTokenizer st = new StringTokenizer(roomOpenReq.getHashtags(), "#");
        String[] hashtagNms = new String[3];
        Integer[] hashtagIds = new Integer[3];
        for (int i = 0; i < 3; i++) {
            if (st.hasMoreTokens()) {
                String hashtag = st.nextToken();
                Optional<RoomHashtag> hashtagOptional = hashtagRepository.findHashtagByNm(hashtag);
                RoomHashtag roomHashtag;
                if (hashtagOptional.isPresent()) {  // ?????? ?????? ?????????
                    roomHashtag = hashtagOptional.get();
                    roomHashtag.setCnt(roomHashtag.getCnt() + 1);
                } else {  //???????????? ?????? ??????????????????
                    roomHashtag = RoomHashtag.builder()
                            .nm(hashtag)
                            .cnt(1L)
                            .build();
                }
                roomHashtag = hashtagRepository.save(roomHashtag);
                hashtagNms[i] = hashtag;
                hashtagIds[i] = roomHashtag.getId();
            }
        }

        // room_info
        User user = userRepository.findUserById(Long.parseLong(sessionID)).get();
        String thumbUrl = saveImage(roomOpenReq.getFile(), sessionID);
        RoomInfo roomInfo = RoomInfo.builder()
                .pwd(roomOpenReq.getPwd())
                .userHost(user)
                .maxNum(roomOpenReq.getMax_num())
                .thumbUrl(thumbUrl)
                .build();
        roomInfo = roomInfoRepository.save(roomInfo);
        // room_panel
        RoomPanel roomPanel = RoomPanel.builder()
                .id(roomInfo.getId())
                .build();
        roomPanelRepository.save(roomPanel);
        // room_description
        RoomDescription roomDescription = RoomDescription.builder()
                .id(roomInfo.getId())
                .title(roomOpenReq.getTitle())
                .subtitle(roomOpenReq.getSubtitle())
                .cate(roomOpenReq.getCate())
                .hash1(hashtagIds[0]).hash2(hashtagIds[1]).hash3(hashtagIds[2])
                .build();
        roomDescriptionRepository.save(roomDescription);

        // ????????? ??????
        RoomInfoDto roomInfoDto = RoomInfoDto.builder()
                .id(roomInfo.getId()).pwd(roomInfo.getPwd())
                .thumbUrl(roomInfo.getThumbUrl()).phase(roomInfo.getPhase())
                .maxNum(roomInfo.getMaxNum()).curNum(roomInfo.getCurNum())
                .startTime(roomInfo.getStartTime())
                .cate(roomOpenReq.getCate())
                .hash1(hashtagNms[0]).hash2(hashtagNms[1]).hash3(hashtagNms[2])
                .title(roomOpenReq.getTitle()).subtitle(roomOpenReq.getSubtitle())
                .build();
        VRoom vRoom = VRoom.builder().code(aes128Util.encrypt(sessionID)).session(session).VSession(null)
                .mapParticipants(new ConcurrentHashMap<>())
                .roomInfoDto(roomInfoDto)
                .agree(new VUserInfo[roomOpenReq.getMax_num()]).disagree(new VUserInfo[roomOpenReq.getMax_num()])
                .build();
        this.mapRooms.put(sessionID, vRoom);

        return this.mapRooms.get(sessionID).getCode();
    }

    @Override
    public boolean isExistRoom(String sessionID) {
        return this.mapRooms.containsKey(sessionID);
    }

    @Override
    public boolean isExistSession(String sessionID) {
        return this.mapRooms.get(sessionID).getVSession() != null;
    }

    @Override
    public boolean isParticipate(String sessionID, String userID) {
        return this.mapRooms.get(sessionID).getMapParticipants().containsKey(userID);
    }

    @Override
    public Long getRoomInfoId(String sessionID) {
        return this.mapRooms.get(sessionID).getRoomInfoDto().getId();
    }

    @Override
    public void deleteRoom(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        this.mapRooms.get(sessionID).getSession().close();
        this.mapRooms.remove(sessionID);
    }

    @Override
    public boolean checkPwd(String sessionID, String pwd) {
        return pwd.equals(this.mapRooms.get(sessionID).getRoomInfoDto().getPwd());
    }

    @Override
    public String decodeCode(String code) {
        return aes128Util.decrypt(code);
    }

    @Override
    public String enterRoom(String sessionID, UserInfoDto userInfoDto) {
        Connection connection;
        VRoom vRoom = this.mapRooms.get(sessionID);
        // ??? ?????? ??????
//        if (!roomEnterReq.getCode().equals(this.mapRooms.get(roomEnterReq.getSessionId()))) return null;
        Session session = vRoom.getSession();

        try {
            // ?????? ??????
            // ????????? ID??? ????????? ID??? ????????? Moderator ??????
            OpenViduRole role;
            if (userInfoDto.getId().equals(session.getSessionId())) role = OpenViduRole.MODERATOR;
            else {  // ??????????????? ???????????? ???????????? ???????????? ??????
                if (userInfoDto.getId() != null) role = OpenViduRole.PUBLISHER;  // ??????
                else role = OpenViduRole.SUBSCRIBER;  // ?????????
            }

            // Connection ??????
            ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                    .role(role).build();
            connection = session.createConnection(connectionProperties);

            vRoom.getRoomInfoDto().setCurNum(vRoom.getRoomInfoDto().getCurNum() + 1);

            // ??? ????????? UserInfo ??????
            VUserInfo vUserInfo = VUserInfo.builder()
                    .id(userInfoDto.getId()).em(userInfoDto.getEm()).nnm(userInfoDto.getNnm())
                    .build();
            vRoom.getMapParticipants().put(userInfoDto.getId(), vUserInfo);
            vRoom.getMapParticipants().get(userInfoDto.getId()).setConnectionDto(new ConnectionDto(connection));

            return connection.getToken();
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            // ??????
            vRoom.getMapParticipants().remove(userInfoDto.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String reconnect(String sessionID, String userID) {
        if (this.mapRooms.get(sessionID).getMapParticipants().containsKey(userID)) {
            return this.mapRooms.get(sessionID).getMapParticipants().get(userID).getConnectionDto().getToken();
        }

        return null;
    }

    @Override
    public Map<String, Map<String, String>> createSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        // ?????? ?????? ?????? ??? ????????? ?????? ??????
        SessionProperties sessionProperties;
        VRoom vRoom = this.mapRooms.get(sessionID);
        Session sessionAgree;
        Session sessionDisagree;
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(OpenViduRole.PUBLISHER).build();
        Connection connection;
        Map<String, ConnectionDto> mapAgree = new ConcurrentHashMap<>();
        Map<String, ConnectionDto> mapDisagree = new ConcurrentHashMap<>();

        // ?????? ?????? ?????? ??? ?????? ??????
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(sessionID + "_agree").build();
            sessionAgree = this.openVidu.createSession(sessionProperties);
            for (VUserInfo VUserInfo : vRoom.getAgree()) {
                if (VUserInfo == null) continue;
                connection = sessionAgree.createConnection(connectionProperties);
                mapAgree.put(VUserInfo.getId(), new ConnectionDto(connection));
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
        // ?????? ?????? ?????? ??? ?????? ??????
        try {
            sessionProperties = new SessionProperties.Builder().customSessionId(sessionID + "_disagree").build();
            sessionDisagree = this.openVidu.createSession(sessionProperties);
            for (VUserInfo VUserInfo : vRoom.getDisagree()) {
                if (VUserInfo == null) continue;
                connection = sessionDisagree.createConnection(connectionProperties);
                mapDisagree.put(VUserInfo.getId(), new ConnectionDto(connection));
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            // ????????? ?????? ???????????? ??????
            assert sessionAgree != null;
            sessionAgree.close();
            throw new RuntimeException(e);
        }

        // mapSessions ??? ?????? ??????
        VSession vSession = VSession.builder()
                .sessionAgree(sessionAgree).sessionDisagree(sessionDisagree)
                .mapAgree(mapAgree).mapDisagree(mapDisagree)
                .build();
        mapRooms.get(sessionID).setVSession(vSession);

        // ??? ???????????? ????????? ???????????? ?????? ?????? ??????
        Map<String, Map<String, String>> mapTokens = new ConcurrentHashMap<>();
        // ?????????
        for (VUserInfo vUserInfo : vRoom.getAgree()) {
            if (vUserInfo == null) continue;
            Map<String, String> map = new HashMap<>();
            map.put("connectionID", vUserInfo.getConnectionDto().getConnectionId());
            map.put("token", mapAgree.get(vUserInfo.getId()).getToken());
            mapTokens.put(vUserInfo.getId(), map);
        }
        // ?????????
        for (VUserInfo vUserInfo : vRoom.getDisagree()) {
            if (vUserInfo == null) continue;
            Map<String, String> map = new HashMap<>();
            map.put("connectionID", vUserInfo.getConnectionDto().getConnectionId());
            map.put("token", mapDisagree.get(vUserInfo.getId()).getToken());
            mapTokens.put(vUserInfo.getId(), map);
        }
        return mapTokens;
    }

    @Override
    public void deleteSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException {
        // ?????? ??????
        this.mapRooms.get(sessionID).getVSession().getSessionAgree().close();
        this.mapRooms.get(sessionID).getVSession().getSessionDisagree().close();

        // ?????? ?????? ??????
        this.mapRooms.get(sessionID).setVSession(null);
    }

    @Override
    public String checkPos(String sessionID, String AToken, String pos, boolean isSelect) {
        // ??????
        VRoom vRoom = this.mapRooms.get(sessionID);

        // ????????? ????????? ?????? ???
        if (vRoom.getRoomInfoDto().getPhase() != 0) return "403";

        int max = vRoom.getRoomInfoDto().getMaxNum();
        VUserInfo[] VUserInfos;

        // ????????? ??????????????? ?????? ??????
        if (pos.equals("agree")) VUserInfos = vRoom.getAgree();
        else if (pos.equals("disagree")) VUserInfos = vRoom.getDisagree();
        else return "400";

        // ????????? ??? ?????????
        for (int i = 0; i < max; i++) {
            if (isSelect && VUserInfos[i] == null) {
                VUserInfos[i] = vRoom.getMapParticipants().get(AToken);
                return "Success";
            }
            else if (!isSelect && VUserInfos[i].getId().equals(AToken)) {
                VUserInfos[i] = null;
                return "Success";
            }
        }
        // ????????? ??? ?????????
        return "Fail";
    }

    @Override
    public Map<String, String> getAgreeConnections(String sessionID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (VUserInfo VUserInfo : vRoom.getAgree()) {
            if (vRoom.getMapParticipants().containsKey(VUserInfo.getId())) {
                ConnectionDto vConn = vRoom.getMapParticipants().get(VUserInfo.getId()).getConnectionDto();
                connections.put(VUserInfo.getId(), vConn.getConnectionId());
            }
        }

        return connections;
    }

    @Override
    public Map<String, String> getDisagreeConnections(String sessionID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        Map<String, String> connections = new ConcurrentHashMap<>();
        for (VUserInfo VUserInfo : vRoom.getDisagree()) {
            if (vRoom.getMapParticipants().containsKey(VUserInfo.getId())) {
                ConnectionDto vConn = vRoom.getMapParticipants().get(VUserInfo.getId()).getConnectionDto();
                connections.put(VUserInfo.getId(), vConn.getConnectionId());
            }
        }

        return connections;
    }

    @Override
    public Map<String, String> getPanelNicknames(String sessionID) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        Map<String, String> mapPanels = new HashMap<>();
        // ?????????
        for (VUserInfo VUserInfo : vRoom.getAgree()) {
            mapPanels.put(VUserInfo.getId(), VUserInfo.getNnm());
        }
        // ?????????
        for (VUserInfo VUserInfo : vRoom.getDisagree()) {
            mapPanels.put(VUserInfo.getId(), VUserInfo.getNnm());
        }
        return mapPanels;
    }

    @Override
    public void increasePenalty(String sessionID, String panel) {
        VRoom vRoom = this.mapRooms.get(sessionID);
        VUserInfo vUserInfo = vRoom.getMapParticipants().get(panel);
        vUserInfo.setPenaltyCnt(vUserInfo.getPenaltyCnt() + 1);
        UserStat userStat = userStatRepository.findStatById(Long.valueOf(panel)).get();
        userStat.setPenalty(vUserInfo.getPenaltyCnt());
        userStatRepository.save(userStat);
    }

    @Override
    public Boolean updatePhaseBySessionID(String sessionID, int phase) {
        RoomInfoDto roomInfoDto = mapRooms.get(sessionID).getRoomInfoDto();

        if (roomInfoDto.getPhase() >= phase || phase < 0 || phase > 4) return false;

        // ?????? ??????
        roomInfoDto.setPhase(phase);

        // DB ??????
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
            if (VUserInfo.isHasDisagree()) { //????????? ???????????????
                VUserInfo.setHasDisagree(false);
                //????????? 1 ??????
                vRoom.setVote_disagree(vRoom.getVote_disagree() - 1);
            }
            //?????? ????????? ????????? ????????????
            if (!VUserInfo.isHasAgree()) {
                //???????????? ?????? ?????? ?????? ????????????
                VUserInfo.setHasAgree(true);
                //????????? 1 ??????
                vRoom.setVote_agree(vRoom.getVote_agree() + 1);
            }
        } else if (vote.equals("disagree")) {
            if (VUserInfo.isHasAgree()) { //????????? ??????????????????
                VUserInfo.setHasAgree(false);
                //????????? 1 ??????
                vRoom.setVote_agree(vRoom.getVote_agree() - 1);
            }
            //?????? ????????? ????????? ????????????
            if (!VUserInfo.isHasDisagree()) {
                //???????????? ?????? ?????? ?????? ????????????
                VUserInfo.setHasDisagree(true);
                //????????? 1 ??????
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

        //?????? ????????? ????????? ?????? ????????????
        if (!VUserInfo.isHasFinalVote()) {
            VUserInfo.setHasFinalVote(true);
            if (vote.equals("agree")) {
                vRoom.setVote_final_agree(vRoom.getVote_final_agree() + 1);
            } else if (vote.equals("disagree")) {
                vRoom.setVote_final_disagree(vRoom.getVote_final_disagree() + 1);
            } else return "400";
        }

        //????????? ????????????
//        if(!vRoom.getMapParticipants().containsKey(kingUserEm)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "User not exists"));
        //????????? ?????? ?????? ??????
        VUserInfo kingUser = vRoom.getMapParticipants().get(kingUserID);
        //????????? ?????? ??? ????????????
        kingUser.setKingCnt(kingUser.getKingCnt() + 1);

        return "200";
    }

    @Override
    @Transactional
    public void finishRoom(String userID) {
        // ?????? ????????? ?????? ???????????? VRoom ??????
        VRoom vRoom = this.mapRooms.get(userID);

        // DB??? ??????
        try {
            RoomInfo roomInfo = roomInfoRepository.findById(vRoom.getRoomInfoDto().getId()).get();
            // ?????? ??????
            short winner = -1;  // ?????????
            if (vRoom.getVote_final_agree() > vRoom.getVote_final_disagree()) winner = 1;
            else if (vRoom.getVote_final_agree() < vRoom.getVote_final_disagree()) winner = 2;
            // ????????? ??????
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

            // room_info ????????????
            roomInfo.setWinner(winner);
            roomInfo.setAgree((short) vRoom.getVote_final_agree());
            roomInfo.setDisagree((short) vRoom.getVote_final_disagree());
            if (kingID != null) roomInfo.setUserKing(userRepository.findUserById(Long.parseLong(kingID)).get());
            roomInfoRepository.save(roomInfo);

            // user history ????????????
            // ?????? ?????? ????????????
            for (VUserInfo VUserInfo : vRoom.getAgree()) {
                if (VUserInfo == null) continue;
                boolean isHost = Objects.equals(roomInfo.getHostId(), Long.parseLong(VUserInfo.getId()));
                boolean isKing = VUserInfo.getId().equals(kingID);

                User user = userRepository.findUserById(Long.parseLong(VUserInfo.getId())).get();
                UserHistory userHistory = UserHistory.builder()
                        .userId(user.getId())
                        .roomId(roomInfo.getId())
                        .isHost(isHost)
                        .isKing(isKing)
                        .userPos(1)
                        .build();
                userHistory.setRoomInfo(roomInfo);
                userHistoryRepository.save(userHistory);

                //user stat ????????????
                UserStat userStat = userStatRepository.findStatById(user.getId()).get();
                userStat.setTotal(userStat.getTotal() + 1);
                //??? , ??? , ???
                if (winner == 1) {
                    userStat.setWin(userStat.getWin() + 1);
                } else if (winner == 2) {
                    userStat.setLose(userStat.getLose() + 1);
                } else {
                    userStat.setDraw(userStat.getDraw() + 1);
                }
                // ?????????
                if (isKing) userStat.setKing(userStat.getKing() + 1);
                userStatRepository.save(userStat);
            }

            //?????? ?????? ????????????
            for (VUserInfo VUserInfo : vRoom.getDisagree()) {
                if (VUserInfo == null) continue;
                boolean isHost = Objects.equals(roomInfo.getHostId(), Long.parseLong(VUserInfo.getId()));
                boolean isKing = VUserInfo.getId().equals(kingID);

                User user = userRepository.findUserById(Long.parseLong(VUserInfo.getId())).get();
                UserHistory userHistory = UserHistory.builder()
                        .userId(user.getId())
                        .roomId(roomInfo.getId())
                        .isHost(isHost)
                        .isKing(isKing)
                        .userPos(1)
                        .build();
                userHistory.setRoomInfo(roomInfo);
                userHistoryRepository.save(userHistory);

                //user stat ????????????
                UserStat userStat = userStatRepository.findStatById(user.getId()).get();
                userStat.setTotal(userStat.getTotal() + 1);
                //??? , ??? , ???
                if (winner == 2) {
                    userStat.setWin(userStat.getWin() + 1);
                } else if (winner == 1) {
                    userStat.setLose(userStat.getLose() + 1);
                } else {
                    userStat.setDraw(userStat.getDraw() + 1);
                }
                // ?????????
                if (isKing) userStat.setKing(userStat.getKing() + 1);
                userStatRepository.save(userStat);
            }
            // openVidu ?????? ??????
            Session session = vRoom.getSession();
            session.close();

            this.mapRooms.remove(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> getResultByRoomId(Long roomID) {
        Optional<RoomInfo> roomInfoOptional = roomInfoRepository.findRoomInfoById(roomID);
        if (!roomInfoOptional.isPresent()) return null;

        Map<String, String> result = new HashMap<>();
        result.put("agree", String.valueOf(roomInfoOptional.get().getAgree()));
        result.put("disagree", String.valueOf(roomInfoOptional.get().getDisagree()));
        result.put("king", roomInfoOptional.get().getUserKing().getNnm());
        return result;
    }

    @Override
    @Scheduled(cron = "0/60 * * * * *")
    public void syncServer() throws OpenViduJavaClientException, OpenViduHttpException {
        // openVidu ?????? ?????????
        this.openVidu.fetch();

        List<Session> sessionList = this.openVidu.getActiveSessions();
        Map<String, Session> activeSessions = new ConcurrentHashMap<>();
        for (Session session : sessionList) {
            activeSessions.put(session.getSessionId(), session);
        }

        List<String> buffer = new ArrayList<>();
        for (String key : this.mapRooms.keySet()) {
            if (!key.chars().allMatch(Character::isDigit)) continue;
            if (!activeSessions.containsKey(key)) {
                buffer.add(key);
            } else {
                VRoom vRoom = mapRooms.get(key);

                vRoom.setSession(activeSessions.get(key));  // ?????? ?????? ??????
                List<Connection> connectionList = activeSessions.get(key).getActiveConnections();
                for (Connection conn : connectionList) {
                    for (String id : vRoom.getMapParticipants().keySet()) {
                        if (vRoom.getMapParticipants().get(id).getConnectionDto().getConnectionId().equals(conn.getConnectionId())) {
                            // Connection ?????? ??????
                            vRoom.getMapParticipants().get(id).setConnectionDto(new ConnectionDto(conn));
                            break;
                        }
                    }
                }
                vRoom.getRoomInfoDto().setCurNum(connectionList.size());

                // DB ??????
                RoomInfo roomInfo = roomInfoRepository.findRoomInfoById(vRoom.getRoomInfoDto().getId()).get();
                roomInfo.setCurNum(vRoom.getRoomInfoDto().getCurNum());
                roomInfo.setPhase(vRoom.getRoomInfoDto().getPhase());
                roomInfoRepository.save(roomInfo);
            }
        }

        for (String key : buffer) this.mapRooms.remove(key);
    }

    @Override
    public void initServer() throws OpenViduJavaClientException, OpenViduHttpException {
        // ?????? ?????????
        this.openVidu.fetch();
        List<Session> list = this.openVidu.getActiveSessions();
        for (Session s : list) s.close();

        // ?????? ?????????
        mapRooms.clear();
    }

    @Override
    public String saveImage(String fileBase64, String sessionID) {
        if (fileBase64 == null || fileBase64.length() == 0) return null;
        File path = new File(thumbPath);
        if (!path.exists()) path.mkdirs();
        try {
            String fileName = System.currentTimeMillis() + "_" + sessionID;
            File file = new File(thumbPath + fileName + ".jpg");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodeBytes = decoder.decode(fileBase64.getBytes());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decodeBytes);
            fileOutputStream.close();
            return fileName + ".jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getLists() {
        return new ArrayList<>(this.mapRooms.keySet());
    }

    @Override
    public Map<String, Map<String, List<VUserInfo>>> getPanels() {
        Map<String, Map<String, List<VUserInfo>>> result = new HashMap<>();
        for (String key : mapRooms.keySet()) {
            Map<String, List<VUserInfo>> temp = new HashMap<>();
            List<VUserInfo> list = new ArrayList<>();
            for (VUserInfo vUserInfo : mapRooms.get(key).getAgree()) {
                if (vUserInfo == null) continue;
                list.add(vUserInfo);
            }
            temp.put("agree", list);

            list.clear();
            for (VUserInfo vUserInfo : mapRooms.get(key).getDisagree()) {
                if (vUserInfo == null) continue;
                list.add(vUserInfo);
            }
            temp.put("disagree", list);
            result.put(key, temp);
        }
        return result;
    }

}

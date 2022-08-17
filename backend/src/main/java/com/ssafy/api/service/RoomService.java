package com.ssafy.api.service;


import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.UserInfoDto;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.io.IOException;
import java.util.Map;

public interface RoomService {

    String createRoom(String sessionID, RoomOpenReq roomOpenReq) throws IOException;

    boolean isExistRoom(String sessionID);

    boolean isExistSession(String sessionID);

    boolean isParticipate(String sessionID, String userID);

    Long getRoomInfoId(String sessionID);

    void deleteRoom(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;

    boolean checkPwd(String sessionID, String pwd);

    String decodeCode(String code);

    String enterRoom(String sessionID, UserInfoDto userInfoDto);

    String reconnect(String sessionID, String userID);

    Map<String, Map<String, String>> createSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;

    void deleteSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;

    String checkPos(String sessionID, String AToken, String pos, boolean isSelect);

    Map<String, String> getAgreeConnections(String sessionID);

    Map<String, String> getDisagreeConnections(String sessionID);

    void increasePenalty(String sessionID, String panel);

    Boolean updatePhaseBySessionID(String sessionID, int phase);

    String updateCheerCnt(String sessionID, String pos);

    String updateVoteMiddle(String sessionID, String userID, String vote);

    String updateVoteFinal(String sessionID, String userID, String vote, String kingUserID);

    void finishRoom(String userID) throws OpenViduJavaClientException, OpenViduHttpException;

    Map<String, String> getResultByRoomId(Long roomID);

    void syncServer() throws OpenViduJavaClientException, OpenViduHttpException;

    void initServer() throws OpenViduJavaClientException, OpenViduHttpException;

    String saveImage(String fileBase64, String roomName);

}
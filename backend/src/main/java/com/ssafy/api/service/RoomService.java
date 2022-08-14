package com.ssafy.api.service;


import com.ssafy.api.request.RoomEnterReq;
import com.ssafy.api.request.RoomOpenReq;
import com.ssafy.db.dto.UserInfoDto;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

import java.util.Map;

public interface RoomService {

    void createRoom(String sessionID, RoomOpenReq roomOpenReq);

    boolean isExistRoom(String sessionID);

    boolean isExistSession(String sessionID);

    boolean isParticipate(String sessionID, String userID);

    void deleteRoom(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;

    boolean checkPwd(String sessionID, String pwd);

    String enterRoom(RoomEnterReq roomEnterReq, UserInfoDto userInfoDto);

    Map<String, Map<String, String>> createSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;
    void deleteSession(String sessionID) throws OpenViduJavaClientException, OpenViduHttpException;

    String checkPos(String sessionID, String AToken, String pos, boolean isSelect);

    Map<String, String> getAgreeConnections(String sessionID);

    Map<String, String> getDisagreeConnections(String sessionID);

    Boolean updatePhaseBySessionID(String sessionID, int phase);

    String updateCheerCnt(String sessionID, String pos);

    String updateVoteMiddle(String sessionID, String userID, String vote);

    String updateVoteFinal(String sessionID, String userID, String vote, String kingUserID);

    Map<String, String> finishRoom(String userID) throws OpenViduJavaClientException, OpenViduHttpException;

    void syncServer() throws OpenViduJavaClientException, OpenViduHttpException;

    void initServer() throws OpenViduJavaClientException, OpenViduHttpException;

}
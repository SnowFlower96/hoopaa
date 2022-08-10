package com.ssafy.common.data;

import com.ssafy.db.dto.RoomInfoDto;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;
import lombok.Data;

import java.util.Map;

/**
 * 토론방 세션의 정보를 관리하기 위한 데이터 객체
 */
@Data
public class VRoom {

    // OpenVidu 세션
    private Session session;

    // 세션에 접속한 모든 사람 Connection
    private Map<String, Connection> mapConnections;

    // 세션에 접속한 모든 사람 정보
    private Map<String, UserInfo> mapParticipants;

    private RoomInfoDto roomInfoDto;  // 방 정보

    private UserInfo[] agree;  // 찬성측 유저 정보 배열
    private UserInfo[] disagree;  // 반대측 유저 정보 배열

    private int cheer_agree;  // 찬성측 응원수
    private int cheer_disagree;  // 반대측 응원수
    private int vote_agree;  // 토론중의 찬성 투표수
    private int vote_disagree;  // 토론중의 반대 투표수
    private int vote_final_agree;  // 최종 단계에서의 찬성 투표수
    private int vote_final_disagree;  // 최종 단계에서의 반대 투표수

    private boolean agree_voice;  // 찬성측 발언권 유무
    private boolean disagree_voice;  // 반대측 발언권 유무

}

package com.ssafy.redis.entity;

import com.ssafy.common.data.VUserInfo;
import com.ssafy.db.dto.RoomInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

/**
 * 토론방 세션의 정보를 관리하기 위한 redis 데이터 객체
 */
@RedisHash("VRoom")
@Data
@NoArgsConstructor
public class VRoom {

    // OpenVidu 커스텀 세션 ID
    @Id
    String sessionID;

    // 생성 시간 UTC milliseconds
    private String createdAt;
    // OpenVidu 세션 ID
    private String id;

    // 세션에 접속한 모든 사람 정보
    private Map<String, VUserInfo> mapParticipants;

    private RoomInfoDto roomInfoDto;  // 방 정보

    private String[] agree;  // 찬성측 유저 ID 배열
    private String[] disagree;  // 반대측 유저 ID 배열

    private int cheer_agree;  // 찬성측 응원수
    private int cheer_disagree;  // 반대측 응원수
    private int vote_agree;  // 토론중의 찬성 투표수
    private int vote_disagree;  // 토론중의 반대 투표수
    private int vote_final_agree;  // 최종 단계에서의 찬성 투표수
    private int vote_final_disagree;  // 최종 단계에서의 반대 투표수

    private boolean agree_voice;  // 찬성측 발언권 유무
    private boolean disagree_voice;  // 반대측 발언권 유무

    @Builder
    public VRoom(String sessionID, String createdAt, String id, Map<String, VUserInfo> mapParticipants, RoomInfoDto roomInfoDto, String[] agree, String[] disagree, int cheer_agree, int cheer_disagree, int vote_agree, int vote_disagree, int vote_final_agree, int vote_final_disagree, boolean agree_voice, boolean disagree_voice) {
        this.sessionID = sessionID;
        this.createdAt = createdAt;
        this.id = id;
        this.mapParticipants = mapParticipants;
        this.roomInfoDto = roomInfoDto;
        this.agree = agree;
        this.disagree = disagree;
        this.cheer_agree = cheer_agree;
        this.cheer_disagree = cheer_disagree;
        this.vote_agree = vote_agree;
        this.vote_disagree = vote_disagree;
        this.vote_final_agree = vote_final_agree;
        this.vote_final_disagree = vote_final_disagree;
        this.agree_voice = agree_voice;
        this.disagree_voice = disagree_voice;
    }
}

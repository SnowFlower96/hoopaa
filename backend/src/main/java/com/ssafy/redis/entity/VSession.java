package com.ssafy.redis.entity;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.Map;

@Data
@ToString
@RedisHash("VSession")
public class VSession {

    @Id
    String sessionID;

    // 찬성 세션 정보
    String agreeSessionID;
    String agreeCreatedAt;
    String agreeID;

    // 반대 세션 정보
    String disagreeSessionID;
    String disagreeCreatedAt;
    String disagreeID;

    // 찬성측 참가자와 Connection
    Map<String, ConnectionDTO> mapAgree;

    // 반대측 참가자와 Connection
    Map<String, ConnectionDTO> mapDisagree;

    @Builder
    public VSession(String sessionID, String agreeSessionID, String agreeCreatedAt, String agreeID, String disagreeSessionID, String disagreeCreatedAt, String disagreeID, Map<String, ConnectionDTO> mapAgree, Map<String, ConnectionDTO> mapDisagree) {
        this.sessionID = sessionID;
        this.agreeSessionID = agreeSessionID;
        this.agreeCreatedAt = agreeCreatedAt;
        this.agreeID = agreeID;
        this.disagreeSessionID = disagreeSessionID;
        this.disagreeCreatedAt = disagreeCreatedAt;
        this.disagreeID = disagreeID;
        this.mapAgree = mapAgree;
        this.mapDisagree = mapDisagree;
    }
}

package com.ssafy.common.data;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class VSession {

    // 찬성 세션
    Session sessionAgree;

    // 반대 세션
    Session sessionDisagree;

    // 찬성측 참가자와 Connection
    Map<String , Connection> mapAgree;

    // 반대측 참가자와 Connection
    Map<String , Connection> mapDisagree;

    @Builder
    public VSession(Session sessionAgree, Session sessionDisagree, Map<String, Connection> mapAgree, Map<String, Connection> mapDisagree) {
        this.sessionAgree = sessionAgree;
        this.sessionDisagree = sessionDisagree;
        this.mapAgree = mapAgree;
        this.mapDisagree = mapDisagree;
    }
}

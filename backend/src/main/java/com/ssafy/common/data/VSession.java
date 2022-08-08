package com.ssafy.common.data;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.Session;
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

}

package com.ssafy.common.vidu;

import io.openvidu.java.client.Connection;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConnectionDto {

    private String connectionId;
    private String status;
    private Long createdAt;
    private Long activeAt;
    private String ip;
    private String platform;
    private String clientData;
    private String token;

    public ConnectionDto(Connection conn) {
        this.connectionId = conn.getConnectionId();
        this.status = conn.getStatus();
        this.createdAt = conn.createdAt();
        this.activeAt = conn.activeAt();
        this.ip = conn.getIp();
        this.platform = conn.getPlatform();
        this.clientData = conn.getClientData();
        this.token = conn.getToken();
    }

}

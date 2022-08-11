package com.ssafy.redis.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ConnectionDTO {

    String connectionID;
    String sessionID;
    String createdAt;
    String ip;
    String token;
    String serverData;
    String clientData;
    String role;

    @Builder
    public ConnectionDTO(String connectionID, String sessionID, String createdAt, String ip, String token, String serverData, String clientData, String role) {
        this.connectionID = connectionID;
        this.sessionID = sessionID;
        this.createdAt = createdAt;
        this.ip = ip;
        this.token = token;
        this.serverData = serverData;
        this.clientData = clientData;
        this.role = role;
    }

}

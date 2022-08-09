package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SessionReq {

    @ApiModelProperty(name = "방 ID", example = "SessionID")
    String sessionId;

    @ApiModelProperty(name = "찬성측 ID", example = "[2, 45, 1, 23, ...]")
    String[] agree;

    @ApiModelProperty(name = "반대측 ID", example = "[2, 45, 1, 23, ...]")
    String[] disagree;

}

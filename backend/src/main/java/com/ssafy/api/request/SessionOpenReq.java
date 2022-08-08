package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SessionOpenReq {

    @ApiModelProperty(name = "방 ID", example = "SessionID")
    String sessionId;

    @ApiModelProperty(name = "찬성측 email", example = "[test@test.test, ...]")
    String[] agree;

    @ApiModelProperty(name = "반대측 email", example = "[test@test.test, ...]")
    String[] disagree;

}

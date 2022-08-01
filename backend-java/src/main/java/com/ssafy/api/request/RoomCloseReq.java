package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoomCloseReq {
    @ApiModelProperty(name = "방 id", example = "1")
    Long id;

    @ApiModelProperty(name = "log url", example = "/dd/dd")
    String log;

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    int winner;

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    int agree;

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    int disagree;

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    int invalid;



}

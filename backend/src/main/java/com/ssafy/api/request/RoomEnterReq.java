package com.ssafy.api.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomEnterReq {

    @ApiModelProperty(name = "방 id", example = "1")
    String sessionId;

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    String pwd;

}


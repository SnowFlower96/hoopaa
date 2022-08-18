package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * OpenVidu Connection 토큰 반환을 위한 응답값 정의
 */
@Getter
@Setter
@ApiModel("ViduTokenResponse")
public class VTokenRes extends BaseResponseBody {

    @ApiModelProperty(name = "OpenVidu Connection Token")
    String token;

    public static VTokenRes of(Integer statusCode, String message, String token) {
        VTokenRes vTokenRes = new VTokenRes();
        vTokenRes.setStatusCode(statusCode);
        vTokenRes.setMessage(message);
        vTokenRes.setToken(token);
        return vTokenRes;
    }

}

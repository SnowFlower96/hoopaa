package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 세부 세션을 위한 다수의 OpenVidu Connection 토큰 반환을 위한 응답값 정의
 */
@Getter
@Setter
@ApiModel("ViduTokensResponse")
public class VTokensRes extends BaseResponseBody {
    @ApiModelProperty(name="OpenVidu Connection Token")
    Map<String, String> tokens;

    public static VTokensRes of(Integer statusCode, String message, Map<String, String> tokens) {
        VTokensRes vTokensRes = new VTokensRes();
        vTokensRes.setStatusCode(statusCode);
        vTokensRes.setMessage(message);
        vTokensRes.setTokens(tokens);
        return vTokensRes;
    }
}
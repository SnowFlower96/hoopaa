package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;

import com.ssafy.db.dto.UserInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 비회원 유저 임시 토큰 발급 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("NonUserLoginResponse")
public class NonUserLoginRes extends BaseResponseBody{
    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String accessToken;
    String nnm;

    public static NonUserLoginRes of(Integer statusCode, String message, String accessToken, String nnm) {
        NonUserLoginRes res = new NonUserLoginRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        res.setNnm(nnm);
        return res;
    }
}

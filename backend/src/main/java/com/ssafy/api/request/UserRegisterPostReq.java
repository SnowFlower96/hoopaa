package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Data
@NoArgsConstructor
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {

	@ApiModelProperty(name = "유저 email", example = "ssafy@ssafy.com")
    String em;
    @ApiModelProperty(name = "유저 Password", example = "your_password")
    String pwd;
    @ApiModelProperty(name = "유저 닉네임", example = "your_nickname")
    String nnm;

    @Builder
    public UserRegisterPostReq(String em, String pwd, String nnm) {
        this.em = em;
        this.pwd = pwd;
        this.nnm = nnm;
    }

}

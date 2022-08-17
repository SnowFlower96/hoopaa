package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * json으로 응답하기 위한 응답값 정의
 */
@Getter
@Setter
@ApiModel("StringResponse")
public class StringRes extends BaseResponseBody {

    @ApiModelProperty(name = "String")
    String response;

    public static StringRes of(Integer statusCode, String message, String str) {
        StringRes stringRes = new StringRes();
        stringRes.setStatusCode(statusCode);
        stringRes.setMessage(message);
        stringRes.setResponse(str);
        return stringRes;
    }

}

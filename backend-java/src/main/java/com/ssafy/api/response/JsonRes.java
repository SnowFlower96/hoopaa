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
@ApiModel("JsonResponse")
public class JsonRes extends BaseResponseBody {
    @ApiModelProperty(name="Json")
    String json;

    public static JsonRes of(Integer statusCode, String message, String json) {
        JsonRes jsonRes = new JsonRes();
        jsonRes.setStatusCode(statusCode);
        jsonRes.setMessage(message);
        jsonRes.setJson(json);
        return jsonRes;
    }

}

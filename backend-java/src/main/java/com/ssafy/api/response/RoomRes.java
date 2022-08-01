package com.ssafy.api.response;

import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.RoomInfo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("RoomResponse")
public class RoomRes extends BaseResponseBody {
    Long id;
    Long host_id;
    int is_sys;
    String thumb_url;
    int phase;
    int max_num;
    int cur_num;
    LocalDateTime start_time;
    String title;
    String subtitle;

    public static RoomRes of(Integer statusCode, String message, RoomInfo roomInfo) {
        RoomRes res = new RoomRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(roomInfo.getId());
        res.setHost_id(roomInfo.getHost_id());
        res.setIs_sys(roomInfo.getIs_sys());
        res.setThumb_url(roomInfo.getThumb_url());
        res.setPhase(roomInfo.getPhase());
        res.setMax_num(roomInfo.getMax_num());
        res.setCur_num(roomInfo.getCur_num());
        res.setStart_time(roomInfo.getStart_time());
        res.setTitle(roomInfo.getTitle());
        res.setSubtitle(roomInfo.getSubtitle());

        return res;
    }

}

package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
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

    public static RoomRes of(Integer statusCode, String message, RoomInfoDto  roomInfoDto) {
        RoomRes res = new RoomRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(roomInfoDto.getId());
        res.setHost_id(roomInfoDto.getHostId());
        res.setIs_sys(roomInfoDto.getIsSys());
        res.setThumb_url(roomInfoDto.getThumbUrl());
        res.setPhase(roomInfoDto.getPhase());
        res.setMax_num(roomInfoDto.getMaxNum());
        res.setCur_num(roomInfoDto.getCurNum());
        res.setStart_time(roomInfoDto.getStartTime());
        res.setTitle(roomInfoDto.getTitle());
        res.setSubtitle(roomInfoDto.getSubtitle());

        return res;
    }

}

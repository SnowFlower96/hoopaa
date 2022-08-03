package com.ssafy.api.response;

import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.dto.RoomInfoDto;
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
    String nnm;

    public static RoomRes of(Integer statusCode, String message, RoomInfoDto  roomInfoDto) {
        RoomRes res = new RoomRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(roomInfoDto.getId());
        res.setHost_id(roomInfoDto.getHost_id());
        res.setIs_sys(roomInfoDto.getIs_sys());
        res.setThumb_url(roomInfoDto.getThumb_url());
        res.setPhase(roomInfoDto.getPhase());
        res.setMax_num(roomInfoDto.getMax_num());
        res.setCur_num(roomInfoDto.getCur_num());
        res.setStart_time(roomInfoDto.getStart_time());
        res.setTitle(roomInfoDto.getTitle());
        res.setSubtitle(roomInfoDto.getSubtitle());
        res.setNnm(roomInfoDto.getNnm());

        return res;
    }

}

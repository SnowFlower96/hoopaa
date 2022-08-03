package com.ssafy.db.dto;

import com.ssafy.config.ColumnConfig;
import com.ssafy.db.entity.RoomInfo;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@ToString
public class RoomInfoDto {

    private Long id;
    private String pwd;
    private Long host_id;
    private int is_sys;
    private String thumb_url;
    private int phase;
    private int max_num;
    private int cur_num;
    private LocalDateTime start_time;
    private Integer cate;
    private Integer hash_1;
    private Integer hash_2;
    private Integer hash_3;
    private String title;
    private String subtitle;
    private String nnm;

    public RoomInfoDto(RoomInfo roomInfo){
        this.id = roomInfo.getId();
        this.pwd = roomInfo.getPwd();
        this.host_id = roomInfo.getHost_id();
        this.is_sys = roomInfo.getIs_sys();
        this.thumb_url = roomInfo.getThumb_url();
        this.phase = roomInfo.getPhase();
        this.max_num = roomInfo.getMax_num();
        this.cur_num = roomInfo.getCur_num();
        this.start_time = roomInfo.getStart_time();
        this.cate = roomInfo.getCate();
        this.hash_1 = roomInfo.getHash_1();
        this.hash_2 = roomInfo.getHash_2();
        this.hash_3 = roomInfo.getHash_3();
        this.title = roomInfo.getTitle();
        this.subtitle = roomInfo.getSubtitle();
        this.nnm= roomInfo.getUser().getNnm();
    }
}

package com.ssafy.db.dto;

import com.ssafy.db.entity.RoomInfo;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class RoomInfoDto {

    private Long id;
    private String pwd;
    private Long hostId;
    private int isSys;
    private String thumbUrl;
    private int phase;
    private int maxNum;
    private int curNum;
    private LocalDateTime startTime;
    private Integer cate;
    private Integer hash1;
    private Integer hash2;
    private Integer hash3;
    private String title;
    private String subtitle;
    private String nnm;

    public RoomInfoDto(RoomInfo roomInfo){
        this.id = roomInfo.getId();
        this.pwd = roomInfo.getPwd();
        this.hostId = roomInfo.getHostId();
        this.isSys = roomInfo.getIsSys();
        this.thumbUrl = roomInfo.getThumbUrl();
        this.phase = roomInfo.getPhase();
        this.maxNum = roomInfo.getMaxNum();
        this.curNum = roomInfo.getCurNum();
        this.startTime = roomInfo.getStartTime();
        this.cate = roomInfo.getCate();
        this.hash1 = roomInfo.getHash1();
        this.hash2 = roomInfo.getHash2();
        this.hash3 = roomInfo.getHash3();
        this.title = roomInfo.getTitle();
        this.subtitle = roomInfo.getSubtitle();
        this.nnm= roomInfo.getUser().getNnm();
    }
}

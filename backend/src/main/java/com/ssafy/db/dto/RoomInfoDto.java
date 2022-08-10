package com.ssafy.db.dto;

import com.ssafy.db.entity.RoomInfo;
import lombok.Builder;
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
    private LocalDateTime startTime;
    private Integer cate;
    private String hash1;
    private String hash2;
    private String hash3;
    private String title;
    private String subtitle;

    @Builder
    public RoomInfoDto(Long id, String pwd, Long hostId, int isSys, String thumbUrl, int phase, int maxNum, LocalDateTime startTime, Integer cate, String hash1, String hash2, String hash3, String title, String subtitle) {
        this.id = id;
        this.pwd = pwd;
        this.hostId = hostId;
        this.isSys = isSys;
        this.thumbUrl = thumbUrl;
        this.phase = phase;
        this.maxNum = maxNum;
        this.startTime = startTime;
        this.cate = cate;
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.hash3 = hash3;
        this.title = title;
        this.subtitle = subtitle;
    }

    public RoomInfoDto(RoomInfo roomInfo){
        this.id = roomInfo.getId();
        this.pwd = roomInfo.getPwd();
        this.hostId = roomInfo.getHostId();
        this.isSys = roomInfo.getIsSys();
        this.thumbUrl = roomInfo.getThumbUrl();
        this.phase = roomInfo.getPhase();
        this.maxNum = roomInfo.getMaxNum();
        this.startTime = roomInfo.getStartTime();
        this.cate = roomInfo.getCate();
        this.title = roomInfo.getTitle();
        this.subtitle = roomInfo.getSubtitle();
    }
}

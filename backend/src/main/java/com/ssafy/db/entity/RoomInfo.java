package com.ssafy.db.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pwd;
    @Column(insertable = false, updatable = false)
    private Long hostId;

    private String thumbUrl;
    private int maxNum;
    private int curNum;
    private int phase;

    @Generated(GenerationTime.INSERT)
    private LocalDateTime startTime;
    @Generated(GenerationTime.INSERT)
    private LocalDateTime endTime;

    private String log;
    @Column(insertable = false, updatable = false)
    private Long kingID;
    private Short winner;
    private Short agree;
    private Short disagree;
    private Short invalid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    RoomDescription roomDescription;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    RoomPanel roomPanel;

    @OneToOne
    @JoinColumn(name = "hostId", referencedColumnName = "id")
    User user;

    @Builder
    public RoomInfo(Long id, String pwd, Long hostId, String thumbUrl, int maxNum, int curNum, int phase, LocalDateTime startTime, LocalDateTime endTime, String log, Long kingID, Short winner, Short agree, Short disagree, Short invalid) {
        this.id = id;
        this.pwd = pwd;
        this.hostId = hostId;
        this.thumbUrl = thumbUrl;
        this.maxNum = maxNum;
        this.curNum = curNum;
        this.phase = phase;
        this.startTime = startTime;
        this.endTime = endTime;
        this.log = log;
        this.kingID = kingID;
        this.winner = winner;
        this.agree = agree;
        this.disagree = disagree;
        this.invalid = invalid;
    }
}
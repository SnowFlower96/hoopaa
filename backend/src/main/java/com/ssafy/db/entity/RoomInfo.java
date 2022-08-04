package com.ssafy.db.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pwd;
    @Column(insertable = false, updatable = false)
    private Long hostId;
    private int isSys;
    private String thumbUrl;
    private int phase;
    private int maxNum;
    private int curNum;
    @Generated(GenerationTime.INSERT)
    private LocalDateTime startTime;
    private Integer cate;
    private Integer hash1;
    private Integer hash2;
    private Integer hash3;
    private String title;
    private String subtitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    RoomHistory roomHistory;

    @OneToOne
    @JoinColumn(name = "hostId", referencedColumnName = "id")
    User user;

    @OneToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    RoomStatus roomStatus;

    @Builder
    public RoomInfo(Long id, String pwd, Long hostId, int isSys, String thumbUrl, int phase, int maxNum, int curNum, LocalDateTime startTime, Integer cate, Integer hash1, Integer hash2, Integer hash3, String title, String subtitle) {
        this.id = id;
        this.pwd = pwd;
        this.hostId = hostId;
        this.isSys = isSys;
        this.thumbUrl = thumbUrl;
        this.phase = phase;
        this.maxNum = maxNum;
        this.curNum = curNum;
        this.startTime = startTime;
        this.cate = cate;
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.hash3 = hash3;
        this.title = title;
        this.subtitle = subtitle;
    }
}

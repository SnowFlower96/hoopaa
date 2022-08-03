package com.ssafy.db.entity;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Date;
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
    private Long host_id;
    private int is_sys;
    private String thumb_url;
    private int phase;
    private int max_num;
    private int cur_num;
    @Generated(GenerationTime.INSERT)
    private LocalDateTime start_time;
    private Integer cate;
    private Integer hash_1;
    private Integer hash_2;
    private Integer hash_3;
    private String title;
    private String subtitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    RoomHistory roomHistory;

    @OneToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    User user;

    @OneToOne
    @JoinColumn(name="id", referencedColumnName = "id")
    RoomStatus roomStatus;

    @Builder
    public RoomInfo(Long id, String pwd, Long host_id, int is_sys, String thumb_url, int phase, int max_num, int cur_num, LocalDateTime start_time, Integer cate, Integer hash_1, Integer hash_2, Integer hash_3, String title, String subtitle) {
        this.id = id;
        this.pwd = pwd;
        this.host_id = host_id;
        this.is_sys = is_sys;
        this.thumb_url = thumb_url;
        this.phase = phase;
        this.max_num = max_num;
        this.cur_num = cur_num;
        this.start_time = start_time;
        this.cate = cate;
        this.hash_1 = hash_1;
        this.hash_2 = hash_2;
        this.hash_3 = hash_3;
        this.title = title;
        this.subtitle = subtitle;
    }
}

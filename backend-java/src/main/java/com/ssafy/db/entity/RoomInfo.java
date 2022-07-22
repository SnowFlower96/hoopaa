package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pwd;
    private int host_id;
    private int is_sys;
    private String thumb_url;
    private int phase;
    private int max_num;
    private int cur_num;
    private Date start_time;
    private int cate;
    private Integer hash_1;
    private Integer hash_2;
    private Integer hash_3;
    private String title;
    private String subtitle;

    @Builder
    public RoomInfo(Long id, String pwd, int host_id, int is_sys, String thumb_url, int phase, int max_num, int cur_num, Date start_time, int cate, Integer hash_1, Integer hash_2, Integer hash_3, String title, String subtitle) {
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

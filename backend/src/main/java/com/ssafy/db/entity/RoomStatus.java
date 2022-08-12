package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity
public class RoomStatus {
    @Id
    private Long id;

    @Column(name = "agree_1")
    private Long agree_1;
    private Long agree_2;
    private Long agree_3;
    private Long agree_4;
    private Long agree_5;

    private Long disagree_1;
    private Long disagree_2;
    private Long disagree_3;
    private Long disagree_4;
    private Long disagree_5;

    private int agree;

    private int disagree;

    @Builder
    public RoomStatus(Long id, Long agree_1, Long agree_2, Long agree_3, Long agree_4, Long agree_5, Long disagree_1, Long disagree_2, Long disagree_3, Long disagree_4, Long disagree_5, int agree, int disagree) {
        this.id = id;
        this.agree_1 = agree_1;
        this.agree_2 = agree_2;
        this.agree_3 = agree_3;
        this.agree_4 = agree_4;
        this.agree_5 = agree_5;
        this.disagree_1 = disagree_1;
        this.disagree_2 = disagree_2;
        this.disagree_3 = disagree_3;
        this.disagree_4 = disagree_4;
        this.disagree_5 = disagree_5;
        this.agree = agree;
        this.disagree = disagree;
    }
}

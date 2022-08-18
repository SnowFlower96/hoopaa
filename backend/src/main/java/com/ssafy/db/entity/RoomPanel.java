package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_panel")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomPanel {

    @Id
    Long id;

    Long agree1;
    Long agree2;
    Long agree3;
    Long agree4;
    Long agree5;

    Long disagree1;
    Long disagree2;
    Long disagree3;
    Long disagree4;
    Long disagree5;

    @Builder
    public RoomPanel(Long id, Long agree1, Long agree2, Long agree3, Long agree4, Long agree5, Long disagree1, Long disagree2, Long disagree3, Long disagree4, Long disagree5) {
        this.id = id;
        this.agree1 = agree1;
        this.agree2 = agree2;
        this.agree3 = agree3;
        this.agree4 = agree4;
        this.agree5 = agree5;
        this.disagree1 = disagree1;
        this.disagree2 = disagree2;
        this.disagree3 = disagree3;
        this.disagree4 = disagree4;
        this.disagree5 = disagree5;
    }

}

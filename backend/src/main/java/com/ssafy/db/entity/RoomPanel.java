package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room_panel")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomPanel {

    @Id
    Long id;

    Integer agree1;
    Integer agree2;
    Integer agree3;
    Integer agree4;
    Integer agree5;

    Integer disagree1;
    Integer disagree2;
    Integer disagree3;
    Integer disagree4;
    Integer disagree5;

    @Builder
    public RoomPanel(Long id, Integer agree1, Integer agree2, Integer agree3, Integer agree4, Integer agree5, Integer disagree1, Integer disagree2, Integer disagree3, Integer disagree4, Integer disagree5) {
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

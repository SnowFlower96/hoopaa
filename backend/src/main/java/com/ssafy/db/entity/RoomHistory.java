package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomHistory {
    @Id
    Long id;

    String log;
    LocalDateTime end_time;
    int winner;
    int agree;
    int disagree;
    int invalid;

    @Builder
    public RoomHistory(Long id, String log, LocalDateTime end_time, int winner, int agree, int disagree, int invalid) {
        this.id = id;
        this.log = log;
        this.end_time = end_time;
        this.winner = winner;
        this.agree = agree;
        this.disagree = disagree;
        this.invalid = invalid;
    }
}

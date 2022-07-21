package com.ssafy.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class RoomHistory {
    @Id
    Long id;

    String log;
    LocalDateTime end_time;
    int winner;
    int agree;
    int disagree;
    int invalid;
}

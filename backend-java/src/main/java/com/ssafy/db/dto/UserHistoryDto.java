package com.ssafy.db.dto;

import com.ssafy.config.ColumnConfig;
import com.ssafy.db.entity.UserHistory;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class UserHistoryDto {

    LocalDateTime start_time;
    LocalDateTime end_time;
    String host_name;
    String title;

    String position;
    int max_num;
    int agree;
    int disagree;
    int invalid;

    boolean is_win;

    public UserHistoryDto(UserHistory userHistory) {
        this.start_time = userHistory.getRoomInfo().getStart_time();
        this.end_time = userHistory.getRoomInfo().getRoomHistory().getEnd_time();
        this.host_name = userHistory.getRoomInfo().getUser().getNnm();
        this.title = userHistory.getRoomInfo().getTitle();
        this.position = ColumnConfig.getUserPosition(userHistory.getUserPos());
        this.max_num = userHistory.getRoomInfo().getMax_num();
        this.agree = userHistory.getRoomInfo().getRoomHistory().getAgree();
        this.disagree = userHistory.getRoomInfo().getRoomHistory().getDisagree();
        this.invalid = userHistory.getRoomInfo().getRoomHistory().getInvalid();
        this.is_win = ColumnConfig.getUserWin(userHistory.getUserPos(), userHistory.getRoomInfo().getRoomHistory().getWinner());
    }

}

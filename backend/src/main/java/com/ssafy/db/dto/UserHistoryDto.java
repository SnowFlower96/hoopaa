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
        this.start_time = userHistory.getRoomInfo().getStartTime();
        this.end_time = userHistory.getRoomInfo().getEndTime();
        this.host_name = userHistory.getRoomInfo().getUser().getNnm();
        this.title = userHistory.getRoomInfo().getRoomDescription().getTitle();
        this.position = ColumnConfig.getUserPosition(userHistory.getUserPos());
        this.max_num = userHistory.getRoomInfo().getMaxNum();
        this.agree = userHistory.getRoomInfo().getAgree();
        this.disagree = userHistory.getRoomInfo().getDisagree();
        this.invalid = userHistory.getRoomInfo().getInvalid();
        this.is_win = ColumnConfig.getUserWin(userHistory.getUserPos(), userHistory.getRoomInfo().getWinner());
    }

}

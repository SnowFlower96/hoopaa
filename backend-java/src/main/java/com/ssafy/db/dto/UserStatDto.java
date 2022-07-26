package com.ssafy.db.dto;

import com.ssafy.db.entity.UserStat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserStatDto {

    int exp;
    int total;
    int win;
    int lose;
    int draw;
    int penalty;
    int king;

    public UserStatDto(UserStat userStat) {
        this.exp = userStat.getExp();
        this.total = userStat.getTotal();
        this.win = userStat.getWin();
        this.lose = userStat.getLose();
        this.draw = userStat.getDraw();
        this.penalty = userStat.getPanelty();
        this.king = userStat.getKing();
    }
}

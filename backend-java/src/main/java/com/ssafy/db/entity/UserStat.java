package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_stat")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStat {
    @Id
    Long id;

    int exp;

    int total;

    int win;

    int lose;

    int draw;

    int panelty;

    int king;

    @Builder
    public UserStat(Long id, int exp, int total, int win, int lose, int draw, int panelty, int king) {
        this.id = id;
        this.exp = exp;
        this.total = total;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.panelty = panelty;
        this.king = king;
    }
}

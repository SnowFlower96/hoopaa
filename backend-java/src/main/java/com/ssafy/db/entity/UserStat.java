package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user_stat")
@Getter
@Setter
@ToString
public class UserStat {
    @Id
    Long id;

    long exp;

    long total;

    long win;

    long lose;

    long draw;

    long penalty;

    long king;

}

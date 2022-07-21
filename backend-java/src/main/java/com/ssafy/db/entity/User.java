package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 유저 모델 정의.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String em;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String pwd;

    String nnm;

    int level;

    boolean em_auth;

    @Generated(GenerationTime.INSERT)
    LocalDateTime join_dt;

    String refresh_tk;

    String access_tk;

    LocalDateTime modify_dt;

    LocalDateTime leave_dt;

    @OneToOne
    @JoinTable(
            name = "user_stat",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id")
    )
    UserStat userStat;

    @Builder
    public User(Long id, String em, String pwd, String nnm, int level, boolean em_auth, LocalDateTime join_dt, String refresh_tk, String access_tk, LocalDateTime modify_dt, LocalDateTime leave_dt) {
        this.id = id;
        this.em = em;
        this.pwd = pwd;
        this.nnm = nnm;
        this.level = level;
        this.em_auth = em_auth;
        this.join_dt = join_dt;
        this.refresh_tk = refresh_tk;
        this.access_tk = access_tk;
        this.modify_dt = modify_dt;
        this.leave_dt = leave_dt;
    }
}

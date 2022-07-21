package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long user_id;
    @Column(insertable = false, updatable = false)
    Long room_id;
    boolean is_host;
    boolean is_king;
    boolean user_pos;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    RoomInfo roomInfo;

    @Builder
    public UserHistory(Long id, Long user_id, Long room_id, boolean is_host, boolean is_king, boolean user_pos) {
        this.id = id;
        this.user_id = user_id;
        this.room_id = room_id;
        this.is_host = is_host;
        this.is_king = is_king;
        this.user_pos = user_pos;
    }
}

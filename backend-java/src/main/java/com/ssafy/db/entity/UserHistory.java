package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long userId;
    @Column(insertable = false, updatable = false)
    Long roomId;
    boolean isHost;
    boolean isKing;
    int userPos;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    RoomInfo roomInfo;

    @Builder
    public UserHistory(Long id, Long userId, Long roomId, boolean isHost, boolean is_king, int userPos) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.isHost = isHost;
        this.isKing = is_king;
        this.userPos = userPos;
    }
}

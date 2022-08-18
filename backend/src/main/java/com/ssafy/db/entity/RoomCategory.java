package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room_category")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomCategory {

    @Id
    Integer id;

    String nm;

    @Builder
    public RoomCategory(Integer id, String nm) {
        this.id = id;
        this.nm = nm;
    }

}

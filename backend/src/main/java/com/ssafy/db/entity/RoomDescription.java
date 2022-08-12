package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room_description")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDescription {

    @Id
    Long id;

    String title;
    String subtitle;

    Integer cate;

    Integer hash1;
    Integer hash2;
    Integer hash3;

    @Builder
    public RoomDescription(Long id, String title, String subtitle, Integer cate, Integer hash1, Integer hash2, Integer hash3) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.cate = cate;
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.hash3 = hash3;
    }

}

package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cate;
    private int hash_1;
    private int hash_2;
    private int hash_3;
    private String title;
    private String subtitle;

    @Builder

    public RoomDescription(Long id, int cate, int hash_1, int hash_2, int hash_3, String title, String subtitle) {
        this.id = id;
        this.cate = cate;
        this.hash_1 = hash_1;
        this.hash_2 = hash_2;
        this.hash_3 = hash_3;
        this.title = title;
        this.subtitle = subtitle;
    }
}

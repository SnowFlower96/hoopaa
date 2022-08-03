package com.ssafy.db.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="hashtag")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nm;

    Long cnt;

    @Generated(GenerationTime.ALWAYS)
    LocalDateTime recent;

    @Builder
    public Hashtag(Integer id, String nm, Long cnt, LocalDateTime recent) {
        this.id = id;
        this.nm = nm;
        this.cnt = cnt;
        this.recent = recent;
    }
}

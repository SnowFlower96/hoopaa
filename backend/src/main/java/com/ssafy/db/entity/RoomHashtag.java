package com.ssafy.db.entity;

import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="room_hashtag")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomHashtag {

    @Id
    Integer id;

    String nm;

    Long cnt;

    @Generated(GenerationTime.ALWAYS)
    LocalDateTime recent;

    @Builder
    public RoomHashtag(Integer id, String nm, Long cnt, LocalDateTime recent) {
        this.id = id;
        this.nm = nm;
        this.cnt = cnt;
        this.recent = recent;
    }
}

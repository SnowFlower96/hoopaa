package com.ssafy.db.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEmNnmDto {

    String em;
    String nnm;
    boolean emDuple;
    boolean nnmDuple;

    @Builder
    public UserEmNnmDto(String em, String nnm, boolean emDuple, boolean nnmDuple) {
        this.em = em;
        this.nnm = nnm;
        this.emDuple = emDuple;
        this.nnmDuple = nnmDuple;
    }

}

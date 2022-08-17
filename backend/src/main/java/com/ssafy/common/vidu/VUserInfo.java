package com.ssafy.common.vidu;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 토론방의 패널 유저 정보와 최종 토론왕 정보를 알기 위한 클래스
 */
@Data
@ToString
public class VUserInfo {

    String id;
    String em;
    String nnm;

    ConnectionDto connectionDto;

    int kingCnt;
    boolean hasAgree;
    boolean hasDisagree;
    boolean hasFinalVote;
    int penaltyCnt;

    @Builder
    public VUserInfo(String id, String em, String nnm, ConnectionDto connectionDto, int kingCnt, boolean hasAgree, boolean hasDisagree, boolean hasFinalVote, int penaltyCnt) {
        this.id = id;
        this.em = em;
        this.nnm = nnm;
        this.connectionDto = connectionDto;
        this.kingCnt = kingCnt;
        this.hasAgree = hasAgree;
        this.hasDisagree = hasDisagree;
        this.hasFinalVote = hasFinalVote;
        this.penaltyCnt = penaltyCnt;
    }

}

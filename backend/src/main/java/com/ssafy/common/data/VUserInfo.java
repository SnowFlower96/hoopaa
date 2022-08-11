package com.ssafy.common.data;

import com.ssafy.redis.entity.ConnectionDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    ConnectionDTO connectionDTO;
    int kingCnt;
    boolean hasAgree;
    boolean hasDisagree;
    boolean hasFinalVote;

    @Builder
    public VUserInfo(String id, String em, String nnm, ConnectionDTO connectionDTO, int kingCnt, boolean hasAgree, boolean hasDisagree, boolean hasFinalVote) {
        this.id = id;
        this.em = em;
        this.nnm = nnm;
        this.connectionDTO = connectionDTO;
        this.kingCnt = kingCnt;
        this.hasAgree = hasAgree;
        this.hasDisagree = hasDisagree;
        this.hasFinalVote = hasFinalVote;
    }
}

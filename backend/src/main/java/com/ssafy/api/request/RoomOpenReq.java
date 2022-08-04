package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RoomOpenReq {
    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    String pwd;

    @ApiModelProperty(name = "host email", example = "abc@ssafy.com")
    String host_em;

    @ApiModelProperty(name = "host 포지션", example = "0")
    long pos;

    @ApiModelProperty(name = "시스템 모드", example = "0")
    int is_sys;

    @ApiModelProperty(name = "thumbnail_url")
    String thumb_url;

    @ApiModelProperty(name = "룸 상태", example = "0")
    int phase;

    @ApiModelProperty(name = "팀당 최대 인원", example = "3")
    int max_num;

    @ApiModelProperty(name = "현재 인원", example = "0")
    int cur_num;

    @ApiModelProperty(name = "주제 카테고리", example = "1")
    Integer cate;

    @ApiModelProperty(name = "해시태그1", example = "깻잎논쟁")
    String hash_1;

    @ApiModelProperty(name = "해시태그2", example = "핫이슈")
    String hash_2;

    @ApiModelProperty(name = "해시태그3", example = "해시태그3")
    String hash_3;

    @ApiModelProperty(name = "방 제목", example = "이게 과연 옳은가?")
    String title;

    @ApiModelProperty(name = "방 자세히 설명", example = "subtitle")
    String subtitle;

}

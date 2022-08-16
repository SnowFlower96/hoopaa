package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
@ToString
public class RoomOpenReq {

    @ApiModelProperty(name = "방 패스워드", example = "abcd")
    String pwd;

    @ApiModelProperty(name = "팀당 최대 인원", example = "3")
    int max_num;

    @ApiModelProperty(name = "주제 카테고리", example = "1")
    Integer cate;

    @ApiModelProperty(name = "해시태그", example = "#깻잎논쟁#핫이슈")
    String hashtags;

    @ApiModelProperty(name = "방 제목", example = "이게 과연 옳은가?")
    String title;

    @ApiModelProperty(name = "방 자세히 설명", example = "subtitle")
    String subtitle;

    @ApiModelProperty(name = "썸네일")
    String file;

}

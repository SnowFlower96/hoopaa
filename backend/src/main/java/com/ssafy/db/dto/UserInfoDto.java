package com.ssafy.db.dto;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel("UserInfoDto")
public class UserInfoDto {

    Long id;
    String em;
    String nnm;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.em = user.getEm();
        this.nnm = user.getNnm();
    }
}

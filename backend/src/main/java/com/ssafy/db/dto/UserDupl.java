package com.ssafy.db.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDupl {

    boolean isEmDupl;
    boolean isNnmDupl;

    public boolean isDuple() {
        return isEmDupl || isNnmDupl;
    }

}

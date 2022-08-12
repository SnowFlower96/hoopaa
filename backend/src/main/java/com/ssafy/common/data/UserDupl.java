package com.ssafy.common.data;

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

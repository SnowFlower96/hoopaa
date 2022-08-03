package com.ssafy.config;

public class ColumnConfig {

    public static String getUserPosition(int status) {
        if (status == 0) return "호스트";
        else if (status == 1) return "찬성";
        else if (status == 2) return "반대";
        else return "방청객";
    }

    // RoomHistory
    // 0 : 무승부
    // 1 : 찬성측 승리
    // 2 : 반대측 승리
    public static boolean getUserWin(int userPos, int result) {
        if (userPos == result) return true;
        else if (result != 0) return false;
        return true;
    }

}

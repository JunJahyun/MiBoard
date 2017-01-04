package com.midasit.miboard;

/**
 * Created by JiyoungPark on 2017. 1. 4..
 */
public class UserInfo {
    public String id;
    public String password;

    /* 싱글톤 사용. 로그인 이후 생성(new)할 일 없는 class */
    static UserInfo g_userInfo = new UserInfo();

    private UserInfo() {}

    public static UserInfo getInstance() {
        return g_userInfo;
    }
}

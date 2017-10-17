package com.song.petLeague.bean.model;

/**
 * Created by song on 2017/4/21.
 */

public class UserInfo {
    private String userId;
    private String name;
    private String  token;

    public UserInfo() {
    }

    public UserInfo(String userId, String name, String token) {
        this.userId = userId;
        this.name = name;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

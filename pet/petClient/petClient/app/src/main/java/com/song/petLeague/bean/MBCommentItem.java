package com.song.petLeague.bean;

/**
 * Created by song on 2017/4/8.
 */

public class MBCommentItem {
    public String id;
    public User uUser;
    public String mUser;
    public String mbContent;

    public MBCommentItem() {
    }

    public MBCommentItem(String id, User uUser, String mUser, String mbContent) {
        this.id = id;
        this.uUser = uUser;
        this.mUser = mUser;
        this.mbContent = mbContent;
    }

    public MBCommentItem(String mUser, String mbContent, User uUser) {
        this.mUser = mUser;
        this.mbContent = mbContent;
        this.uUser = uUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getuUser() {
        return uUser;
    }

    public void setuUser(User uUser) {
        this.uUser = uUser;
    }

    public String getMbContent() {
        return mbContent;
    }

    public void setMbContent(String mbContent) {
        this.mbContent = mbContent;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }
}













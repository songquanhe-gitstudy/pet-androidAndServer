package com.song.petLeague.bean;

import java.util.List;

/**
 * Created by song on 2017/4/8.
 */

public class MessageBoardItem {

    public static final String TYPE_TRUENAME = "1";


    private String id;
    private User uUser;
    private User mUser;
    private String mContent;
    private String date;
    private String type;     //真名留言或者匿名
    private List<MBCommentItem> MBCommentList;

    public MessageBoardItem() {
    }

    public MessageBoardItem(String id, User uUser, User mUser, String mContent, String date, String type, List<MBCommentItem> MBCommentList) {
        this.id = id;
        this.uUser = uUser;
        this.mUser = mUser;
        this.mContent = mContent;
        this.date = date;
        this.type = type;
        this.MBCommentList = MBCommentList;
    }

    public static String getTypeTruename() {
        return TYPE_TRUENAME;
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

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MBCommentItem> getMBCommentList() {
        return MBCommentList;
    }

    public void setMBCommentList(List<MBCommentItem> MBCommentList) {
        this.MBCommentList = MBCommentList;
    }

    public boolean hasComent() {
        if(MBCommentList != null && MBCommentList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}

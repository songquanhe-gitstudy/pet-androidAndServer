package com.backend.domain;

public class MBoardComment {
    private Integer id;

    private Integer uId;

    private Integer mId;

    private String mbContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getMbContent() {
        return mbContent;
    }

    public void setMbContent(String mbContent) {
        this.mbContent = mbContent == null ? null : mbContent.trim();
    }
}
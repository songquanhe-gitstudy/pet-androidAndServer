package com.backend.domain;

public class MessageBoard {
    private Integer id;

    private Integer ubId;

    private Integer umId;

    private String mContent;

    private String mData;

    private String mType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUbId() {
        return ubId;
    }

    public void setUbId(Integer ubId) {
        this.ubId = ubId;
    }

    public Integer getUmId() {
        return umId;
    }

    public void setUmId(Integer umId) {
        this.umId = umId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent == null ? null : mContent.trim();
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData == null ? null : mData.trim();
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType == null ? null : mType.trim();
    }
}
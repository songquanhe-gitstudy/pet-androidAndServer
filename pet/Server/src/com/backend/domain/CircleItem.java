package com.backend.domain;

public class CircleItem {
    private Integer cId;

    private Integer uId;

    private String cContent;

    private String cDate;

    private String cType;

    private String cLinkImg;

    private String cLinkTitle;

    private String cVideoUrl;

    private String cVideoImgUrl;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate == null ? null : cDate.trim();
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
    }

    public String getcLinkImg() {
        return cLinkImg;
    }

    public void setcLinkImg(String cLinkImg) {
        this.cLinkImg = cLinkImg == null ? null : cLinkImg.trim();
    }

    public String getcLinkTitle() {
        return cLinkTitle;
    }

    public void setcLinkTitle(String cLinkTitle) {
        this.cLinkTitle = cLinkTitle == null ? null : cLinkTitle.trim();
    }

    public String getcVideoUrl() {
        return cVideoUrl;
    }

    public void setcVideoUrl(String cVideoUrl) {
        this.cVideoUrl = cVideoUrl == null ? null : cVideoUrl.trim();
    }

    public String getcVideoImgUrl() {
        return cVideoImgUrl;
    }

    public void setcVideoImgUrl(String cVideoImgUrl) {
        this.cVideoImgUrl = cVideoImgUrl == null ? null : cVideoImgUrl.trim();
    }
}
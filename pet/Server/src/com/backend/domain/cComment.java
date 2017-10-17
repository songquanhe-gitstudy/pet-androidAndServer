package com.backend.domain;

public class cComment {
    private Integer cCid;

    private Integer cId;

    private Integer uId;

    private Integer cTorepId;

    private String cContent;

    public Integer getcCid() {
        return cCid;
    }

    public void setcCid(Integer cCid) {
        this.cCid = cCid;
    }

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

    public Integer getcTorepId() {
        return cTorepId;
    }

    public void setcTorepId(Integer cTorepId) {
        this.cTorepId = cTorepId;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }
}
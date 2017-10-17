package com.backend.domain;

public class cPicture {
    private Integer pId;

    private Integer cId;

    private String pPicUrl;

    private String pWight;

    private String pHight;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getpPicUrl() {
        return pPicUrl;
    }

    public void setpPicUrl(String pPicUrl) {
        this.pPicUrl = pPicUrl == null ? null : pPicUrl.trim();
    }

    public String getpWight() {
        return pWight;
    }

    public void setpWight(String pWight) {
        this.pWight = pWight == null ? null : pWight.trim();
    }

    public String getpHight() {
        return pHight;
    }

    public void setpHight(String pHight) {
        this.pHight = pHight == null ? null : pHight.trim();
    }
}
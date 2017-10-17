package com.song.petLeague.bean;

/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfo {

    public int pId;
    public String url;
    public int w;
    public int h;

    public PhotoInfo() {

    }

    public PhotoInfo(int pId, String url, int w, int h) {
        this.pId = pId;
        this.url = url;
        this.w = w;
        this.h = h;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}

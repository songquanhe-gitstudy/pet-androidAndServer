package com.song.petLeague.bean;

public class News {
    private Integer nId;

    private Integer type;

    private String nTitle;

    private String nAuthor;

    private String nContent;

    private String nPicUrl;

    private String nDate;

    private Integer nPraiseNum;

    private Integer nCommentNum;

    public News() {
    }

    public News(Integer nId, Integer type, String nTitle, String nAuthor,
                String nContent, String nPicUrl, String nDate, Integer nPraiseNum,
                Integer nCommentNum) {
        this.nId = nId;
        this.type = type;
        this.nTitle = nTitle;
        this.nAuthor = nAuthor;
        this.nContent = nContent;
        this.nPicUrl = nPicUrl;
        this.nDate = nDate;
        this.nPraiseNum = nPraiseNum;
        this.nCommentNum = nCommentNum;
    }

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle == null ? null : nTitle.trim();
    }

    public String getnAuthor() {
        return nAuthor;
    }

    public void setnAuthor(String nAuthor) {
        this.nAuthor = nAuthor == null ? null : nAuthor.trim();
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent == null ? null : nContent.trim();
    }

    public String getnPicUrl() {
        return nPicUrl;
    }

    public void setnPicUrl(String nPicUrl) {
        this.nPicUrl = nPicUrl == null ? null : nPicUrl.trim();
    }

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public Integer getnPraiseNum() {
        return nPraiseNum;
    }

    public void setnPraiseNum(Integer nPraiseNum) {
        this.nPraiseNum = nPraiseNum;
    }

    public Integer getnCommentNum() {
        return nCommentNum;
    }

    public void setnCommentNum(Integer nCommentNum) {
        this.nCommentNum = nCommentNum;
    }
}
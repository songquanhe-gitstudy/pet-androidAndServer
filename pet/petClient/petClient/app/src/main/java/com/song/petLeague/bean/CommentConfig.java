package com.song.petLeague.bean;

/**
 * Created by yiwei on 16/3/2.
 */
public class CommentConfig {
    public static enum Type{
        PUBLIC("public"), REPLY("reply");

        private String value;
        private Type(String value){
            this.value = value;
        }

    }

    public String uId;          //用户的id
    public String uName;        //用名字
    public String circleId;    //帖子的id
    public int circlePosition;  //帖子的位置
    public int commentPosition;   //评论的位置
    public Type commentType;      //评论的类型 回复还是评论
    public User replyUser;      //被评论的人

    @Override
    public String toString() {
        String replyUserStr = "";
        if(replyUser != null){
            replyUserStr = replyUser.toString();
        }
        return "circlePosition = " + circlePosition
                + "; commentPosition = " + commentPosition
                + "; commentType ＝ " + commentType
                + "; replyUser = " + replyUserStr;
    }
}

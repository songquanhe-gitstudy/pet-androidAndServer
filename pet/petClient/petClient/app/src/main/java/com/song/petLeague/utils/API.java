package com.song.petLeague.utils;

/**
 * Created by song on 2017/3/19.
 * 接口地址工具类
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p/>
 * ━━━━━━感觉萌萌哒━━━━━━
 */

public class API {

    //服务器接口地址
    public static final String PET_SERVER_URL = "http://115.159.113.18:8080/petServer";
    //登录接口地址
    public static final String LOGIN_URL = PET_SERVER_URL + "/User/getUserList.html?username=%s&password=%s";
    //注册接口地址
    public static final String REGISTER_URL = PET_SERVER_URL + "/User/getRegister.html?username=%s&password=%s&phone=%s&sex=%s&college=%s&city=%s&birthday=%s";
    //圈子数据接口地
    public static final String CIRCLED_ATAS_URL = PET_SERVER_URL + "/Circle/getCircleList.html?&currentPage=%s&circleType=%s&uId=%s";
    //删除点赞接口地址
    public static final String DELETE_PRAISE_URL = PET_SERVER_URL + "/Priase/deletePraise.html?circleId=%s&favortUserId=%s";
    //点赞接口地址
    public static final String ADD_PRAISE_URL = PET_SERVER_URL + "/Priase/addPraise.html?circleId=%s&favortUserId=%s";
    //删除帖子接口地址
    public static final String DELETE_CIRCLE_URL = PET_SERVER_URL + "/Circle/deleteCircle.html?circleId=%s";
    //评论接口地址
    public static final String ADD_COMMENT_URL = PET_SERVER_URL + "/Comment/addRepComment.html?content=%s&circleId=%s&uId=%s&repId=%s";
    //回复评论接口地址
    public static final String ADD_REPLY_URL = PET_SERVER_URL + "/Comment/addComment.html?content=%s&circleId=%s&uId=%s";
    //删除自己的评论接口地址
    public static final String DELETE_COMMMENT_URL = PET_SERVER_URL + "/Comment/deleteComment.html?circleId=%s";
    //发布帖子接口地址
    public static final String ADD_POST = PET_SERVER_URL + "/Circle/savePhotos.html";
    //背景图片修改接口地址
    public static final String CHANGE_PIC_URL = PET_SERVER_URL + "/User/savePictureBg.html";

    //个人详细信息头像修改接口地址
    public static final String CHANGE_PERSONALDETAILS_PIC_URL = PET_SERVER_URL + "/User/savePicture.html";
    //判断是否关注接口地址
    public static final String IF_ATTENTION_URL = PET_SERVER_URL + "/UserFrient/judgeAttention.html";
    //关注朋友接口地址
    public static final String ATTENTION_FRIENT_URL = PET_SERVER_URL + "/UserFrient/attentionFrients.html";
    //取消关注朋友接口地址
    public static final String CANCLE_ATTENTION_URL = PET_SERVER_URL + "/UserFrient/cancelAttention.html";
    //获取关注总数接口地址
    public static final String ATTENTION_COUNTS = PET_SERVER_URL + "/UserFrient/attentionCounts.html";
    //获取粉丝总数接口地址
    public static final String FENS_COUNTS = PET_SERVER_URL + "/UserFrient/fensCounts.html";
    //获取粉丝总数接口地址
    public static final String CIRCLE_COUNTS = PET_SERVER_URL + "/UserFrient/circleCounts.html";

    //获取所有的图片接口地址
    public static final String ALL_PICTRUES = PET_SERVER_URL + "/UserFrient/getAllPicture.html";

    //获取关注人数据接口地址
    public static final String LOAD_ATTENTION_PEOPLE = PET_SERVER_URL + "/UserFrient/loadAttentionData.html?uId=%s&currentPage=%s";
    //获取粉丝人数据接口地址
    public static final String LOAD_FENS_PEOPLE = PET_SERVER_URL + "/UserFrient/loadFensData.html?uId=%s&currentPage=%s";
    //获取留言数据接口地址
    public static final String LOAD_MESSAGE_BOARD = PET_SERVER_URL + "/MessageBoard/getMessageList.html?uId=%s&currentPage=%s&mId=%s";
    //删除留言数据接口地址
    public static final String DELETE_MESSAGE_BOARD = PET_SERVER_URL + "/MessageBoard/deleteMessageBoard.html?uId=%s";
    //增加留言中的评论数据接口地址
    public static final String ADD_BOARD_COMMENT = PET_SERVER_URL + "/MessageBoard/addBoardCommnet.html?content=%s&uId=%s&msId=%s";
    //增加留言的数据接口地址
    public static final String ADD_MESSAGE_BOARD = PET_SERVER_URL + "/MessageBoard/addMessageBoard.html?ubId=%s&umId=%s&mContent=%s&boardType=%s";

    //请求获得Token
    public static final String GET_TOKEN = PET_SERVER_URL + "/User/getToken.html?id=%s&name=%s&portraitUri=%s";

    //新闻接口地址
    public static final String NEWS_URL = PET_SERVER_URL + "/News/getNewsList.html?type=%s&page=%s";
}

package com.song.petLeague.utils;

import android.text.TextUtils;
import android.util.Log;

import com.song.petLeague.bean.CircleItem;
import com.song.petLeague.bean.CommentItem;
import com.song.petLeague.bean.FavortItem;
import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.bean.News;
import com.song.petLeague.bean.PhotoInfo;
import com.song.petLeague.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * json解析工具类
 */
public class JsonUtils {

    //登录用户的相关信息jso数据---------------------------------------------------------------
    public static User parseUserJson(String json) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(removeBOM(json));
            JSONObject data = jsonObject.optJSONObject("data");
            JSONObject myUser = data.optJSONObject("myUser");
            String id = myUser.getString("id");
            String name = myUser.getString("name");
            String pwd = myUser.getString("pwd");
            String headUrl = myUser.getString("headUrl");
            String headBgUrl = myUser.getString("headBgUrl");
            String uPhoneNumber = myUser.getString("uPhoneNumber");
            String uSex = myUser.getString("uSex");
            String uAge = myUser.getString("uAge");
            String uCollege = myUser.getString("uCollege");
            String uMajor = myUser.getString("uMajor");
            String uClass = myUser.getString("uClass");
            String uStudentNumber = myUser.getString("uStudentNumber");
            String uCity = myUser.getString("uCity");
            String uBirthday = myUser.getString("uBirthday");
            String uSignature = myUser.getString("uSignature");
            String uConstellation = myUser.getString("uConstellation");
            String uEmotion = myUser.getString("uEmotion");
            String uUsuallyCity = myUser.getString("uUsuallyCity");
            String uHabbies = myUser.getString("uHabbies");
            String uLikeSomething = myUser.getString("uLikeSomething");


            Log.e(TAG, "parseUserJson: headBgUrl :" + headBgUrl);
            user = new User(id, name, pwd, headUrl, headBgUrl,
                    uPhoneNumber, uSex, uAge, uCollege,
                    uMajor, uClass, uStudentNumber, uCity,
                    uBirthday, uSignature, uConstellation,
                    uEmotion, uUsuallyCity, uHabbies,
                    uLikeSomething);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

     /* <p/>
     * @param json 网络下载 帖子 中所有相关数据------------------------------------------------
     * @return
     */
    public static List<CircleItem> parseChapterJson(String json) {
        String videoUrl = null;
        String videoImgUrl = null;
        List<CircleItem> list = new ArrayList<>();
        List<PhotoInfo> photoList = null;
        List<FavortItem> favortList = null;
        List<CommentItem> commentList = null;
        try {
            JSONObject object = new JSONObject(removeBOM(json));
            JSONObject data = object.optJSONObject("data");
            for (int index = 0; index < data.length(); index++) {
                photoList = new ArrayList<>();
                favortList = new ArrayList<>();
                commentList = new ArrayList<>();
                //根据键值对来进行json解析
                JSONObject jsonObject = (JSONObject) data.get(index + "");
                String id = jsonObject.getString("id");
//                String uId = jsonObject.getString("uId");
                //用户对象
                JSONObject uId = jsonObject.optJSONObject("uId");
                String userHeadUrl = uId.getString("uHeadUrl");
                String userHeadBgUrl = uId.getString("uHeadBgUrl");
                String userName = uId.getString("uName");
                String userPwd = uId.getString("uPwd");
                String userId = String.valueOf(uId.getString("id"));
                String uPhoneNumber = String.valueOf(uId.getString("uPhoneNumber"));
                String uSex = String.valueOf(uId.getString("uSex"));
                String uAge = String.valueOf(uId.getString("uAge"));
                String uCollege = String.valueOf(uId.getString("uCollege"));
                String uMajor = String.valueOf(uId.getString("uMajor"));
                String uClass = String.valueOf(uId.getString("uClass"));
                String uStudentNumber = String.valueOf(uId.getString("uStudentNumber"));
                String uCity = String.valueOf(uId.getString("uCity"));
                String uBirthday = String.valueOf(uId.getString("uBirthday"));
                String uSignature = String.valueOf(uId.getString("uSignature"));
                String uConstellation = String.valueOf(uId.getString("uConstellation"));
                String uEmotion = String.valueOf(uId.getString("uEmotion"));
                String uUsuallyCity = String.valueOf(uId.getString("uUsuallyCity"));
                String uHabbies = String.valueOf(uId.getString("uHabbies"));
                String uLikeSomething = String.valueOf(uId.getString("uLikeSomething"));

                User circleUser = new User(userId, userName, userPwd, userHeadUrl, userHeadBgUrl,
                        uPhoneNumber, uSex, uAge, uCollege,
                        uMajor, uClass, uStudentNumber, uCity,
                        uBirthday, uSignature, uConstellation,
                        uEmotion, uUsuallyCity, uHabbies,
                        uLikeSomething);

                //照片对象
                JSONObject photos = jsonObject.optJSONObject("photos");
                for (int num = 0; num < photos.length(); num++) {
                    PhotoInfo photoInfo = new PhotoInfo();
                    JSONObject photoObject = (JSONObject) photos.get(num + "");
                    String picUrl = photoObject.getString("picUrl");
                    String width = photoObject.getString("width");
                    String height = photoObject.getString("height");
                    photoInfo.setUrl(picUrl);
                    photoInfo.setH(Integer.parseInt(height));
                    photoInfo.setW(Integer.parseInt(width));
                    photoList.add(photoInfo);
                }
                //点赞对象
                JSONObject praises = jsonObject.optJSONObject("praises");
                for (int num = 0; num < praises.length(); num++) {
                    User user = new User();
                    FavortItem favortItem = new FavortItem();
                    JSONObject praiseObject = (JSONObject) praises.get(num + "");
                    String pid = praiseObject.getString("id");
                    //点赞的用户属性
                    String puId = praiseObject.getString("uId");
                    String puName = praiseObject.getString("puName");
                    String puHeadUrl = praiseObject.getString("puHeadUrl");
                    String puHeadBgUrl = praiseObject.getString("puHeadBgUrl");
                    user.setId(puId);
                    user.setName(puName);
                    user.setHeadUrl(puHeadUrl);
                    user.setHeadBgUrl(puHeadBgUrl);

                    favortItem.setId(id);
                    favortItem.setUser(user);
                    favortList.add(favortItem);
                }

                //评论对象
                JSONObject comments = jsonObject.optJSONObject("comments");
                for (int num = 0; num < comments.length(); num++) {
                    User cUser = new User();
                    CommentItem commentItem = new CommentItem();
                    JSONObject praiseObject = (JSONObject) comments.get(num + "");
                    String cid = praiseObject.getString("id");
                    String cuId = praiseObject.getString("cuId");
                    String cuName = praiseObject.getString("cuName");
                    if (!praiseObject.getString("toRepId").equals("null0")) {
                        User toRepUser = new User();
                        String toRepId = praiseObject.getString("toRepId");
                        String toRepName = praiseObject.getString("toRepName");
                        toRepUser.setId(toRepId);
                        toRepUser.setName(toRepName);
                        commentItem.setToReplyUser(toRepUser);
                    }
                    String cContent = praiseObject.getString("cContent");
                    commentItem.setId(cid);
                    commentItem.setContent(cContent);
                    cUser.setId(cuId);
                    cUser.setName(cuName);
                    commentItem.setUser(cUser);

                    commentList.add(commentItem);
                }

                String typeid = jsonObject.getString("typeid");
                String content = jsonObject.getString("content");
                String createTime = jsonObject.getString("date");
                Log.e(TAG, "typeid---------: " + typeid);
                if(typeid.equals("3")) {

                    videoUrl = jsonObject.getString("videoUrl");
                    Log.e(TAG, "videoUrl-----------: " + videoUrl);
                    videoImgUrl = jsonObject.getString("videoImgUrl");
                }


                CircleItem datas = new CircleItem(id, createTime, content, typeid, videoUrl, videoImgUrl, circleUser, photoList, favortList, commentList);
                list.add(datas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //关注对象集合json数据----------------------------------------------------------------------
    public static List<User> parseAttentionJson(String json) {
        List<User> userList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(removeBOM(json));
            JSONObject data = jsonObject.optJSONObject("data");
            for(int num = 0; num < data.length(); num ++){
                JSONObject dataObject = (JSONObject)data.get(num + "");
                JSONObject user = dataObject.optJSONObject("user");
                String userId = String.valueOf(user.getString("id"));
                String userName = user.getString("uName");
                String userHeadUrl = user.getString("uHeadUrl");
                String userHeadBgUrl = user.getString("uHeadBgUrl");
                String uPhoneNumber = user.getString("uPhoneNumber");
                String uSex = user.getString("uSex");
                String uCollege = user.getString("uCollege");
                String uCity = user.getString("uCity");
                String uBirthday = user.getString("uBirthday");

                User userItem = new User(userId, userName, userHeadUrl, userHeadBgUrl,
                        uPhoneNumber, uSex, uCollege, uCity, uBirthday);
                userList.add(userItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    //所有图片对象集合json数据----------------------------------------------------------------------
    public static List<PhotoInfo> parseAllPicturesJson(String json) {
        List<PhotoInfo> photoList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(removeBOM(json));
            JSONObject data = jsonObject.optJSONObject("data");
            for(int num = 0; num < data.length(); num ++){
                JSONObject dataObject = (JSONObject)data.get(num + "");
                JSONObject picture = dataObject.optJSONObject("picture");
                int pId = Integer.parseInt(picture.getString("pId"));
                String pPicUrl = picture.getString("pPicUrl");
                int pWight = Integer.parseInt(picture.getString("pWight"));
                int pHight = Integer.parseInt(picture.getString("pHight"));

                PhotoInfo photos = new PhotoInfo(pId, pPicUrl, pWight, pHight);
                photoList.add(photos);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoList;
    }


    //新闻模块相关json数据----------------------------------------------------------------------
    public static List<News> parseNewsJson(String json) {
        List<News> newList = new ArrayList<>();
        News news = null;

        try {
        JSONObject jsonObject = new JSONObject((removeBOM(json)));
        JSONObject data = jsonObject.optJSONObject("data");
        for (int i = 0; i < data.length(); i++) {
            news = new News();
            JSONObject dataObject = (JSONObject) data.get(i + "");
            int id = dataObject.getInt("id");
            int typeid = dataObject.getInt("typeid");
            String title = dataObject.getString("title");
            String senddate = dataObject.getString("senddate");
            String author = dataObject.getString("author");
            String picUrl = dataObject.getString("picUrl");
            String content = dataObject.getString("content");
            int commentNum = dataObject.getInt("commentNum");
            int praiseNum = dataObject.getInt("praiseNum");

            news = new News(id, typeid, title, author, content, picUrl, senddate, praiseNum, commentNum);
            newList.add(news);

        }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return newList;
    }

    //留言板块相关json数据-------------------------------------------------------------------------
    public static List<MessageBoardItem> parserBoardJson(String json) {
        List<MessageBoardItem> boardItemList = new ArrayList<>();
        List<MBCommentItem> mbCommentList = null;
        try {
            JSONObject jsonObject = new JSONObject(removeBOM(json));
            JSONObject data = jsonObject.optJSONObject("data");
            for (int num = 0; num < data.length(); ++num) {
                mbCommentList = new ArrayList<>();

                JSONObject dataObject = (JSONObject) data.get("" + num);
                JSONObject commentObject = dataObject.optJSONObject("comment");

                for (int index = 0; index < commentObject.length(); ++index) {
                    JSONObject jsonObject1 = (JSONObject) commentObject.get("" + index);
                    String id = String.valueOf(jsonObject1.getString("id"));
                    String mId = String.valueOf(jsonObject1.getString("mId"));
                    String cContent = jsonObject1.getString("content");
                    JSONObject uUser = jsonObject1.optJSONObject("uUser");
                    String uId = String.valueOf(uUser.getString("id"));
                    String uName = uUser.getString("uName");
                    String uHeadUrl = uUser.getString("uHeadUrl");
                    String uHeadBgUrl = uUser.getString("uHeadBgUrl");
                    User uUserItem = new User(uId, uName, uHeadUrl, uHeadBgUrl);

                    MBCommentItem mbCommentItem = new MBCommentItem(id, uUserItem, mId, cContent);
                    mbCommentList.add(mbCommentItem);
                }

                JSONObject ubUser = dataObject.optJSONObject("ubUser");
                String uId1 = String.valueOf(ubUser.getString("id"));
                String uName1 = ubUser.getString("uName");
                String uHeadUrl1 = ubUser.getString("uHeadUrl");
                String uHeadBgUrl1 = ubUser.getString("uHeadBgUrl");
                User ubUserItem = new User(uId1, uName1, uHeadUrl1, uHeadBgUrl1);

                JSONObject umUser = dataObject.optJSONObject("umUser");
                String uId2 = String.valueOf(umUser.getString("id"));
                String uName2 = umUser.getString("uName");
                String uHeadUrl2 = umUser.getString("uHeadUrl");
                String uHeadBgUrl2 = umUser.getString("uHeadBgUrl");
                User umUserItem = new User(uId2, uName2, uHeadUrl2, uHeadBgUrl2);

                String mid = String.valueOf(dataObject.getString("mId"));
                String mContent = dataObject.getString("mContent");
                String mDate = dataObject.getString("mData");
                String type = dataObject.getString("type");


                MessageBoardItem messageBoardItem = new MessageBoardItem(mid, ubUserItem,
                        umUserItem, mContent, mDate, type, mbCommentList);
                boardItemList.add(messageBoardItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return boardItemList;
    }


    /**
     * 异常信息：org.json.JSONException: Value ﻿ of type java.lang.String cannot be converted to JSONObject
     * json串头部出现字符："\ufeff" 解决方法
     * @param data
     * @return
     */
    public static final String removeBOM(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }

        if (data.startsWith("\ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }

}

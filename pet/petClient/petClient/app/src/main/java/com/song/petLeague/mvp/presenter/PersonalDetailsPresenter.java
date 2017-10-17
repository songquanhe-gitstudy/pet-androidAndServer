package com.song.petLeague.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.song.petLeague.bean.PhotoInfo;
import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.contract.PersonalDetailsContract;
import com.song.petLeague.mvp.modle.PersonalDetailsModel;
import com.song.petLeague.utils.PreUtils;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.song.petLeague.utils.JsonUtils.parseAllPicturesJson;

/**
 * Created by song on 2017/4/2.
 */

public class PersonalDetailsPresenter implements PersonalDetailsContract.Presenter{

    private PersonalDetailsModel personalDetailsModel;
    private PersonalDetailsContract.View view;

    public PersonalDetailsPresenter(PersonalDetailsContract.View view) {
        personalDetailsModel = new PersonalDetailsModel();
        this.view = view;
    }

    @Override
    public void changeHeadBgUrl() {


    }

    //修改头像
    @Override
    public void changeHeadUrl(String cutPath, String uId) {
        personalDetailsModel.changeHeadUrl(cutPath, uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                PreUtils.putString("headUrl", object.toString(), (Context) view);
            }
        });
    }

    //判断是否关注
    @Override
    public void judgeAttention(String UId, String frientId) {
        personalDetailsModel.judgeAttention(UId, frientId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.update2JudgeAttention(object.toString());
                }
            }
        });
    }

    //取消关注
    @Override
    public void cancelAttention(String UId, String frinetId) {
        personalDetailsModel.cancelAttention(UId, frinetId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.updata2CancleAttention(object.toString());
                }
            }
        });
    }

    //关注
    @Override
    public void attentionFrient(String uId, String frientId) {
        personalDetailsModel.attentionFrient(uId, frientId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.updata2attentionFrient(object.toString());
                }
            }
        });
    }

    //关注的总数
    @Override
    public void attentionCounts(String uId) {
        personalDetailsModel.attentionCounts(uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.updata2AttentionCounts(object.toString());
                }
            }
        });
    }

    //粉丝总数
    @Override
    public void fensCounts(String uId) {
        personalDetailsModel.fensCounts(uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.updata2fensCounts(object.toString());
                }
            }
        });
    }

    //圈子总数
    @Override
    public void circleCounts(String uId) {
        personalDetailsModel.circleCounts(uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if(view != null) {
                    view.updata2circleCounts(object.toString());
                }
            }
        });
    }

    //所有发的图片
    @Override
    public void allPictures(String uId) {
        personalDetailsModel.allPictures(uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                Log.e(TAG, "loadSuccess图片结果: " + object.toString());
                List<PhotoInfo> infoList = parseAllPicturesJson(object.toString());
                Log.e(TAG, "loadSuccess图片对象: " +  infoList);
                if(view != null) {
                    view.updata2AllPicture(infoList);
                }
            }
        });
    }
}

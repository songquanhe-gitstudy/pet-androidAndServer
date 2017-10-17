package com.song.petLeague.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.song.petLeague.bean.User;
import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.contract.PersonalAttentionContract;
import com.song.petLeague.mvp.modle.PersonalAttentionModel;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.song.petLeague.utils.JsonUtils.parseAttentionJson;

/**
 * Created by song on 2017/4/4.
 */

public class PersonAttenionPresenter implements PersonalAttentionContract.Presenter {

    private PersonalAttentionContract.View view;
    private PersonalAttentionModel personalDetailsModel;

    public PersonAttenionPresenter(PersonalAttentionContract.View view) {
        personalDetailsModel = new PersonalAttentionModel((Context) view);
        this.view = view;
    }


    @Override
    public void loadData(final int type, int currentPage, String uId) {
        personalDetailsModel.requestServer2loadData(currentPage, uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                Log.e(TAG, "关注loadSuccess: " + object.toString());
                List<User> dataLists = parseAttentionJson(object.toString());
                Log.e(TAG, "关注对象loadSuccess: " + dataLists);

                if(view != null) {
                    view.updata2LoadData(type, dataLists);
                }
            }
        });
    }

    @Override
    public void loadFensData(final int type, int currentPage, String uId) {
        personalDetailsModel.requestServer2loadFensData(currentPage, uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                Log.e(TAG, "关注loadSuccess: " + object.toString());
                List<User> dataLists = parseAttentionJson(object.toString());
                Log.e(TAG, "关注对象loadSuccess: " + dataLists);

                if(view != null) {
                    view.updata2LoadFensData(type, dataLists);
                }
            }
        });

    }



    public void recycle() {
        this.view = null;
    }

}




















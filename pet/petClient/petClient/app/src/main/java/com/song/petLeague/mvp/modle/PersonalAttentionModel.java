package com.song.petLeague.mvp.modle;

import android.content.Context;
import android.util.Log;

import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.modle.Imodle.IPersonalAttentionModel;
import com.song.petLeague.utils.API;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/4/4.
 */

public class PersonalAttentionModel implements IPersonalAttentionModel{

    private Context context;

    public PersonalAttentionModel(Context context) {
        this.context = context;

    }

    @Override
    public void requestServer2loadData(final int currentPage, final String uId, final IDataRequestListener listener) {
        Log.e(TAG, "requestServer2loadData:currentPage= "+ currentPage+ ",  uId=" + uId);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("currentPage", String.valueOf(currentPage))
                .add("uId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.LOAD_ATTENTION_PEOPLE).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = new String(response.body().string());
                listener.loadSuccess(result);
            }
        });

    }

    @Override
    public void requestServer2loadFensData(int currentPage, String uId,final IDataRequestListener listener) {
        Log.e(TAG, "requestServer2loadData:currentPage2= "+ currentPage+ ",  uId=" + uId);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("currentPage", String.valueOf(currentPage))
                .add("uId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.LOAD_FENS_PEOPLE).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = new String(response.body().string());
                listener.loadSuccess(result);
            }
        });
    }
}


















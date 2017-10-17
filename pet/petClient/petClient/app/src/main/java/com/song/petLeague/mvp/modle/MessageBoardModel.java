package com.song.petLeague.mvp.modle;

import android.content.Context;

import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.modle.Imodle.IMessageBoardModel;
import com.song.petLeague.utils.API;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by song on 2017/4/10.
 */

public class MessageBoardModel implements IMessageBoardModel {
    private Context context;

    public MessageBoardModel(Context context) {
        this.context = context;
    }

    @Override
    public void requestServer2LoadDate(int currentPage, String uId, String mId, final IDataRequestListener listener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("currentPage", String.valueOf(currentPage))
                .add("uId", uId)
                .add("mId", mId)
                .build();
        Request requst = new Request.Builder()
                .post(body)
                .url(API.LOAD_MESSAGE_BOARD)
                .build();
        Call call = mOkHttpClient.newCall(requst);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                listener.loadSuccess(result);
            }
        });

    }

    @Override
    public void requestServer2DeleteMessageBoard(String uId, final IDataRequestListener listener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(API.DELETE_MESSAGE_BOARD)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("200")) {
                    listener.loadSuccess(result);
                }
            }
        });
    }

    @Override
    public void requestServer2AddBoardComment(String content, String uId, String msId, final IDataRequestListener listener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("content", content)
                .add("uId", uId)
                .add("msId", msId)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(API.ADD_BOARD_COMMENT)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("200")) {
                    listener.loadSuccess(result);
                }
            }
        });
    }

    @Override
    public void requestServer2AddMessageBoard(String ubId, String umId, String mContent, String boardType, final IDataRequestListener listener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("ubId", ubId)
                .add("umId", umId)
                .add("mContent", mContent)
                .add("boardType", boardType)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(API.ADD_MESSAGE_BOARD)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("200")) {
                    listener.loadSuccess(result);
                }
            }
        });
    }

}

package com.song.petLeague.mvp.modle;

import android.util.Log;

import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.utils.API;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by song on 2017/4/2.
 */

public class PersonalDetailsModel {

    public PersonalDetailsModel() {
    }

    public void changeHeadUrl(String cutPath, String uId, final IDataRequestListener listener) {
        requestServer2ChangeHeadUrl(cutPath, uId, listener);
    }

    public void attentionFrient(String uId, String frientId, final IDataRequestListener listener) {
        requestServer2AttentionFrient(uId, frientId, listener);
    }

    public void judgeAttention(String uId, String frientId, final IDataRequestListener listener) {
        requestServer2JudgeAttention(uId, frientId, listener);
    }

    public void cancelAttention(String uId, String frientId, final IDataRequestListener listener) {
        requestServer2CancelAttention(uId, frientId, listener);
    }

    public void attentionCounts(String uId, final IDataRequestListener listener) {
        requestServer2AttentionCounts(uId, listener);
    }

    public void fensCounts(String uId, final IDataRequestListener listener) {
        requestServer2FensCounts(uId, listener);
    }

    public void circleCounts(String uId, final IDataRequestListener listener) {
        requestServer2CircleCounts(uId, listener);
    }

    public void allPictures(String uId, final IDataRequestListener listener) {
        requestServer2AllPictures(uId, listener);
    }


    //修改头像
    private void requestServer2ChangeHeadUrl(String cutPath, String uId, final IDataRequestListener listener) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("*/image");
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder mbody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(cutPath);
        if(file.exists()) {
            //第一个参数是服务器接收的名称，第二个是上传文件的名字，第三个是上传的文件
            mbody.addFormDataPart("picture", file.getName(), RequestBody.create(MEDIA_TYPE_PNG,
                    file));
        }
        mbody.addFormDataPart("uId", uId);
        RequestBody requestBody =mbody.build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(API.CHANGE_PERSONALDETAILS_PIC_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("返回失败的结果： ", " " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if(!result.equals("100")) {
                    listener.loadSuccess(result);
                }
            }
        });
    }

    //判断是否关注
    private void requestServer2JudgeAttention(String uId, String frientId,final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .add("frientId", frientId)
                .build();
        Request request = new Request.Builder().post(body).url(API.IF_ATTENTION_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    //关注成为朋友
    private void requestServer2AttentionFrient(String uId, String frientId,final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .add("frientId", frientId)
                .build();
        Request request = new Request.Builder().post(body).url(API.ATTENTION_FRIENT_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(str.equals("200")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    private void requestServer2CancelAttention(String uId, String frientId, final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .add("frientId", frientId)
                .build();
        Request request = new Request.Builder().post(body).url(API.CANCLE_ATTENTION_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    //关注的总个数
    private void requestServer2AttentionCounts(String uId, final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.ATTENTION_COUNTS).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    private void requestServer2FensCounts(String uId, final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("frientId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.FENS_COUNTS).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    private void requestServer2CircleCounts(String uId, final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("frientId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.CIRCLE_COUNTS).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

    private void requestServer2AllPictures(String uId, final IDataRequestListener listener) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("uId", uId)
                .build();
        Request request = new Request.Builder().post(body).url(API.ALL_PICTRUES).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if(!str.equals("100")) {
                    listener.loadSuccess(str);
                }
            }
        });
    }

}

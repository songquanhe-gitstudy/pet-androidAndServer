package com.song.petLeague.mvp.modle;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.song.petLeague.bean.CommentConfig;
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

import static android.content.ContentValues.TAG;

/**
 * 
* @ClassName: CircleModel 
* @Description: 因为逻辑简单，这里我就不写model的接口了
* @author yiw
* @date 2015-12-28 下午3:54:55 
 */
public class CircleModel {
	private Context context;

	public CircleModel(Context context){
		this.context = context;
	}

	public void loadData( final int currentPage, final int circleType, final String uId, final IDataRequestListener listener){
		requestServer(currentPage, circleType, uId, listener);
	}
	
	public void deleteCircle(final String circleId, final IDataRequestListener listener) {
		requestServer2DeleteCircle(circleId, listener);
	}

	public void addFavort(String circleId, String favortUserId, final IDataRequestListener listener) {
		requestServer2AddFavort(circleId, favortUserId, listener);
	}

	public void deleteFavort(String circleId, String favortUserId, final IDataRequestListener listener) {
		requestServer2DeleteFavort(circleId, favortUserId, listener);
	}

	public void addComment(final String content, final CommentConfig config, final IDataRequestListener listener) {
		requestServer2AddComment(content, config, listener);
	}

	public void deleteComment(final String commentId, final IDataRequestListener listener) {
		requestServer2DeleteCommnet(commentId, listener);
	}

	public void changePicBg(final String picBgPath, final String uId, final IDataRequestListener listener) {
		requestServer2ChangePic(picBgPath, uId, listener);
	}


	/**
	 * 
	* @Title: requestServer 返回圈子里的帖子数据
	* @Description: 与后台交互
	* @param  listener    设定文件
	* @return void    返回类型 
	* @throws
	 */
	private void requestServer(final int currentPage, final int circleType, final String uId, final IDataRequestListener listener) {
		OkHttpClient mOkHttpClient=new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("currentPage", String.valueOf(currentPage))
				.add("circleType", String.valueOf(circleType))
				.add("uId", uId)
				.build();

		Request request  = new Request.Builder().post(body).url(API.CIRCLED_ATAS_URL).build();
		//可以省略，默认是GET请求
		Call mcall= mOkHttpClient.newCall(request);
		mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
				Log.e(TAG, "onFailure: ---e--- :" + e);
			}

            @Override
            public void onResponse(Call call, Response response) throws IOException {

				if (null != response.cacheResponse()) {
					String str = response.cacheResponse().toString();
					Log.i("song", "cache---" + str);
				}else {
					String json = new String(response.body().string());
					listener.loadSuccess(json);
				}
            }
		});
	}

	//请求服务器删除点赞记录
	private void requestServer2DeleteFavort(String circleId, String favortUserId, final IDataRequestListener listener) {
		//创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("circleId", circleId)
				.add("favortUserId", favortUserId)
				.build();
		Request request = new Request.Builder().post(body).url(API.DELETE_PRAISE_URL).build();
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
				if (str.equals("200")) {
					//成功
					listener.loadSuccess(str);
				}
			}
		});
	}

	//请求服务器增加点赞记录
	private void requestServer2AddFavort(String circleId, String favortUserId, final IDataRequestListener listener) {
		//创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("circleId", circleId)
				.add("favortUserId", favortUserId)
				.build();
		Request request = new Request.Builder().post(body).url(API.ADD_PRAISE_URL).build();
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

					listener.loadSuccess(str);
			}
		});
	}

	//请求服务器删除帖子
	public void requestServer2DeleteCircle(String circleId, final IDataRequestListener listener) {
		//创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("circleId", circleId)
				.build();
		Request request = new Request.Builder().post(body).url(API.DELETE_CIRCLE_URL).build();
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

	//请求服务器增加评论
	private void requestServer2AddComment(String content, CommentConfig config, final IDataRequestListener listener) {
		String URL = null;
		//创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = null;
		if(config.replyUser != null){
			URL = API.ADD_COMMENT_URL;
			body = new FormBody.Builder()
					.add("circleId", config.circleId)
					.add("uId", config.uId)
					.add("repId", config.replyUser.getId())
					.add("content", content)
					.build();
		}else {
			URL = API.ADD_REPLY_URL;
			body = new FormBody.Builder()
					.add("circleId", config.circleId)
					.add("uId", config.uId)
					.add("content", content)
					.build();
		}
		Request request = new Request.Builder().post(body).url(URL).build();
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

	//删除自己的评论
	private void requestServer2DeleteCommnet(String commentId,final IDataRequestListener listener) {
		//创建一个OkHttpClient对象
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("circleId", commentId)
				.build();
		Request request = new Request.Builder().post(body).url(API.DELETE_COMMMENT_URL).build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			//请求失败时调用
			@Override
			public void onFailure(Call call, IOException e) {
				Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
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

	private void requestServer2ChangePic(String picBgPath, String uId, final IDataRequestListener listener) {
		MediaType MEDIA_TYPE_PNG = MediaType.parse("*/image");
		OkHttpClient client = new OkHttpClient();
		MultipartBody.Builder mbody=new MultipartBody.Builder().setType(MultipartBody.FORM);
		File file = new File(picBgPath);
			if(file.exists()) {
				//第一个参数是服务器接收的名称，第二个是上传文件的名字，第三个是上传的文件
				mbody.addFormDataPart("picBg", file.getName(), RequestBody.create(MEDIA_TYPE_PNG,
						file));
			}
		mbody.addFormDataPart("uId", uId);
		RequestBody requestBody =mbody.build();
		Request request = new Request.Builder()
				.header("Authorization", "Client-ID " + "...")
				.url(API.CHANGE_PIC_URL)
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

}
package com.song.petLeague.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.song.petLeague.bean.CircleItem;
import com.song.petLeague.bean.CommentConfig;
import com.song.petLeague.bean.CommentItem;
import com.song.petLeague.bean.FavortItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.contract.CircleContract;
import com.song.petLeague.mvp.modle.CircleModel;
import com.song.petLeague.utils.JsonUtils;
import com.song.petLeague.utils.PreUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements CircleContract.Presenter{
	private CircleModel circleModel;
	private CircleContract.View view;
	private Context context;

	public CirclePresenter(CircleContract.View view, Context context){
		circleModel = new CircleModel(context);
		this.view = view;
		this.context = context;
	}

	//假数据
	/*public void loadData(int loadType){

        List<CircleItem> datas = DatasUtil.createCircleDatas();
        if(view!=null){
            view.update2loadData(loadType, datas);
        }
	}*/

	//网络数据
	public void loadData(final int loadType, final int currentPage, final int circleType, final String uId){
		circleModel.loadData(currentPage, circleType, uId,new IDataRequestListener() {
			@Override
			public void loadSuccess(Object object) {
				Log.e(TAG, "loadSuccess: ----" + object);
				List<CircleItem> CircleItemDatas = JsonUtils.parseChapterJson((String)object);
//				List<CircleItem> lists = addData(CircleItemDatas);
				if(view!=null){
					view.update2loadData(loadType, CircleItemDatas);
				}
			}
		});
	}

	private List<CircleItem> addData(List<CircleItem> CircleItemDatas) {
		List<CircleItem> datas = new ArrayList<CircleItem>();
		for (CircleItem items: CircleItemDatas) {
			CircleItem item = new CircleItem();
			item.setId(items.getId());
			item.setType(items.getType());
			item.setCreateTime(items.getCreateTime());
			item.setContent(items.getContent());
			item.setVideoUrl(items.getVideoUrl());
			item.setVideoImgUrl(items.getVideoImgUrl());
			item.setUser(items.getUser());
			item.setFavorters(items.getFavorters());
			item.setComments(items.getComments());
			item.setPhotos(items.getPhotos());

			datas.add(item);
		}
		return datas;

	}


	/**
	 * 
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId, final int circlePosition){
		circleModel.deleteCircle(circleId, new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){

                    view.update2DeleteCircle(circleId, circlePosition);
                }
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition, final String circleId, final String favortUserId){
		circleModel.addFavort(circleId, favortUserId, new IDataRequestListener() {
			@Override
			public void loadSuccess(Object object) {
				final String pId = object.toString();
				User user = new User();
				user.setId(favortUserId);
				user.setName(PreUtils.getString("username", context));
				FavortItem item = new FavortItem();
				item.setId(pId);
				item.setUser(user);
                if(view !=null ){
					view.update2AddFavorite(circlePosition, item);
				}

			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int CirclePosition, final String circleId, final String favortUserId){
		circleModel.deleteFavort(circleId, favortUserId, new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view !=null ){
                    view.update2DeleteFavort(CirclePosition, favortUserId);
                }
			}
		});
	}
	
	/**
	 * 
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		circleModel.addComment(content, config, new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem commentItem = null;
				User user = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					commentItem = new CommentItem();
					user = new User();
					user.setId(config.uId);
					user.setName(config.uName);
					commentItem.setContent(content);
					commentItem.setUser(user);

				} else if (config.commentType == CommentConfig.Type.REPLY) {
					commentItem = new CommentItem();
					user = new User();
					user.setId(config.uId);
					user.setName(config.uName);
					commentItem.setContent(content);
					commentItem.setUser(user);
					commentItem.setToReplyUser(config.replyUser);
				}
                if(view!=null){
                    view.update2AddComment(config.circlePosition, commentItem);
                }
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		circleModel.deleteComment(commentId, new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteComment(circlePosition, commentId);
                }
			}
		});
	}

	@Override
	public void changePicBg(String picBgPath, String uId) {
		circleModel.changePicBg(picBgPath, uId, new IDataRequestListener() {
			@Override
			public void loadSuccess(Object object) {
				PreUtils.putString("headBgUrl", object.toString(), context);
			}
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
        if(view != null){
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
	}


    /**
     * 清除对外部对象的引用，防止内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}

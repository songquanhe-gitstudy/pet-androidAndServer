package com.song.petLeague.mvp.contract;

import com.song.petLeague.bean.CircleItem;
import com.song.petLeague.bean.CommentConfig;
import com.song.petLeague.bean.CommentItem;
import com.song.petLeague.bean.FavortItem;

import java.util.List;

/**
 * Created by suneee on 2016/7/15.
 */
public interface CircleContract {

    interface View extends BaseView{
        void update2DeleteCircle(String circleId, int circlePosition);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int CirclePosition, String favortUserId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleItem> datas);
    }

    interface Presenter extends BasePresenter{
        void loadData(int loadType, int currentPage, int circleType, String uId);
        void deleteCircle(final String circleId, final int circlePosition);
        void addFavort(final int circlePosition, final String circleId, final String favortUserId);
        void deleteFavort(final int CirclePosition, final String circleId, final String favortUserId);
        void deleteComment(final int circlePosition, final String commentId);
        void changePicBg(final String picBgUrl, final String uId);

    }
}

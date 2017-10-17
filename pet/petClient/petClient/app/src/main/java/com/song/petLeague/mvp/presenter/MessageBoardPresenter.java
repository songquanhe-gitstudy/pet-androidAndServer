package com.song.petLeague.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.listener.IDataRequestListener;
import com.song.petLeague.mvp.contract.MessageBoardContract;
import com.song.petLeague.mvp.modle.MessageBoardModel;
import com.song.petLeague.utils.JsonUtils;
import com.song.petLeague.utils.PreUtils;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/4/10.
 */

public class MessageBoardPresenter implements MessageBoardContract.Presener {
    private MessageBoardContract.View view;
    private MessageBoardModel messageBoardModel;

    public MessageBoardPresenter(MessageBoardContract.View view) {
        messageBoardModel = new MessageBoardModel((Context)view);
        this.view = view;
    }

    @Override
    public void loadData(final int refreshType, int currentPage, String uId, String mId) {
        messageBoardModel.requestServer2LoadDate(currentPage, uId, mId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                List<MessageBoardItem> messageBoardItemList = JsonUtils.parserBoardJson(object.toString());
                Log.e(TAG, "loadSuccess: " + messageBoardItemList);

                if (view != null) {
                    view.updata2LoadData(refreshType, messageBoardItemList);
                }
            }
        });
    }

    @Override
    public void deleteMessageBoard(final int boardPosition, String uId) {
        messageBoardModel.requestServer2DeleteMessageBoard(uId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.updata2DeleteMessageBoard(boardPosition);
                }
            }
        });
    }

    @Override
    public void addBoardComment(final String content, String uId, final String msId, final int boardPosition) {
        messageBoardModel.requestServer2AddBoardComment(content, uId, msId, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                User userInfo = PreUtils.getUserInfo((Context) view);
                MBCommentItem mbCommentItem = new MBCommentItem(msId, content, userInfo);
                if (view != null) {
                    view.updata2AddBoardComment(boardPosition, mbCommentItem);
                }
            }
        });

    }

    @Override
    public void addMessageBoard(String ubId, String umId, String mContent, String boardType) {
        messageBoardModel.requestServer2AddMessageBoard(ubId, umId, mContent, boardType, new IDataRequestListener() {
            @Override
            public void loadSuccess(Object object) {
                if (view != null) {
                    view.updata2AddMessageBoard();
                }
            }
        });
    }

    //
    public void showEditTextBody(String uId, String msId, int boardPosition) {
        if (view != null) {
            view.updata2showEditTextBodyVisiable(View.VISIBLE, uId, msId, boardPosition);
        }
    }
}

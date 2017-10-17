package com.song.petLeague.mvp.modle.Imodle;

import com.song.petLeague.listener.IDataRequestListener;

/**
 * Created by song on 2017/4/10.
 */

public interface IMessageBoardModel {
    void requestServer2LoadDate(final int currentPage, final String uId, final String mId,final IDataRequestListener listener);
    void requestServer2DeleteMessageBoard(final String uId, final IDataRequestListener listener);
    void requestServer2AddBoardComment(String content, String uId, String msId, final IDataRequestListener listener);
    void requestServer2AddMessageBoard(String ubId, String umId, String mContent, String boardType, final IDataRequestListener listener);
}

package com.song.petLeague.mvp.modle.Imodle;

import com.song.petLeague.listener.IDataRequestListener;

/**
 * Created by song on 2017/4/4.
 */

public interface IPersonalAttentionModel {

    void requestServer2loadData(final int current, final String uId, final IDataRequestListener listener);

    void requestServer2loadFensData(final int current, final String uId, final IDataRequestListener listener);

}

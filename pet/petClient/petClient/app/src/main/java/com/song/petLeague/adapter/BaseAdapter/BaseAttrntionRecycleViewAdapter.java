package com.song.petLeague.adapter.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.song.petLeague.listener.RecycleViewItemListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * RecycleView的基类
 */
public abstract class BaseAttrntionRecycleViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected RecycleViewItemListener itemListener;
    protected List<T> datas = new ArrayList<>();
    public List<T> getDatas() {
        if (datas==null)
            datas = new ArrayList<>();
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas.addAll(datas);
        Log.e(TAG, "setDatasNums: " + this.datas.size());
    }

    public void RemoveAll() {
        this.datas.clear();
        Log.e(TAG, "RemoveAll: " + datas);
    }

    public void Remove(int circlePosition) {
        this.datas.remove(circlePosition);
    }


    public void setItemListener(RecycleViewItemListener listener){
        this.itemListener = listener;
    }

}

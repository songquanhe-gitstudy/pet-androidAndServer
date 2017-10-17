package com.song.petLeague.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.song.petLeague.R;
import com.song.petLeague.adapter.BaseAdapter.BaseAttrntionRecycleViewAdapter;
import com.song.petLeague.adapter.viewholder.PersonalAttentionViewHolder;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.presenter.PersonAttenionPresenter;
import com.song.petLeague.utils.GlideCircleTransform;
import com.song.petLeague.utils.ToastUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/4/3.
 */

public class PersonalAttentionAdapter extends BaseAttrntionRecycleViewAdapter {

    private Context context;
    private User user;

    private PersonalAttentionViewHolder viewHolder;
    private PersonAttenionPresenter presenter;

    public void setPresenter(PersonAttenionPresenter presenter) {
        this.presenter = presenter;
    }

    public PersonalAttentionAdapter(Context context) {
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_per_atten_item, parent, false);
        viewHolder = new PersonalAttentionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        final PersonalAttentionViewHolder holder = (PersonalAttentionViewHolder) viewHolder;

        user = (User)datas.get(position);
        String name = user.getName();
        String headUrl = user.getHeadUrl();
        String headBgUrl = user.getHeadBgUrl();


        Glide.with(context).load(headUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context))
                .into(holder.ivHead);
        holder.tvName.setText(name);




        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: " + "点击了");
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView, pos);
            }
        });

        //长按点击事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = holder.getLayoutPosition();
                mOnItemClickListener.onItemLongClick(holder.itemView, pos);

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

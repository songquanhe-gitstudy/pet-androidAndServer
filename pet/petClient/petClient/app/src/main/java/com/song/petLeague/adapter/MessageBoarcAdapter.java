package com.song.petLeague.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.song.petLeague.R;
import com.song.petLeague.adapter.BaseAdapter.BaseRecycleViewAdapter;
import com.song.petLeague.adapter.viewholder.MessageBoardViewHolder;
import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.presenter.MessageBoardPresenter;
import com.song.petLeague.utils.GlideCircleTransform;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.widgets.boardCommentListView;
import com.song.petLeague.widgets.dialog.MessageBoardDialog;

import java.util.List;

/**
 * Created by song on 2017/4/8.
 */

public class MessageBoarcAdapter extends BaseRecycleViewAdapter{

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;
    public static final int TYPE_HEAD = 0;
    private Context context;
    private MessageBoardPresenter presenter;
    private User userInfo;

    public void setMessageBoardPresener(MessageBoardPresenter presener) {
        this.presenter = presener;
    }

    public MessageBoarcAdapter(Context context) {
        this.context = context;
    }

    //让头部显示出来
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //返回帖子的类型
    @Override
    public int getItemViewType(int position) {
        int itemType = 0;
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            itemType = 1;
        }
        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        userInfo = PreUtils.getUserInfo(context);
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_messageboard_iv, parent, false);
            viewHolder = new HeaderViewHolder(view);    //让头部局先显示出来
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_board_item, parent, false);
            viewHolder = new MessageBoardViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEAD) {

        } else {
            final int boardPosition = position -1;
            MessageBoardViewHolder holder = (MessageBoardViewHolder) viewHolder;
            final MessageBoardItem boardItem = (MessageBoardItem) datas.get(boardPosition);
            String id = boardItem.getId();
            String content = boardItem.getmContent();
            String type = boardItem.getType();
            String date = boardItem.getDate();
            String headUrl = boardItem.getuUser().getHeadUrl();
            String name = boardItem.getuUser().getName();
            final List<MBCommentItem> mbCommentList = boardItem.getMBCommentList();
            boolean hasComent = boardItem.hasComent();

        if (type.equals("2")) {
            holder.rlStivckyView.setBackgroundResource(R.color.bar_grey);
            holder.ivContent.setBackgroundResource(R.color.bar_grey);
            holder.llCommentBody.setBackgroundResource(R.color.bar_grey);
            holder.diriver.setBackgroundResource(R.color.bar_grey_driver);

            if (position == 1) {
                Glide.with(context).load(R.drawable.anonomity_icon).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                holder.tvName.setText("匿名");
                holder.tvTime.setText(date);
                holder.itemView.getTag(FIRST_STICKY_VIEW);
            } else {
                if (!TextUtils.equals(boardItem.getId(), ((MessageBoardItem)datas.get(boardPosition - 1)).getId())) {
                    holder.rlStivckyView.setVisibility(View.VISIBLE);
                    Glide.with(context).load(R.drawable.anonomity_icon).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                    holder.tvName.setText("匿名");
                    holder.tvTime.setText(date);
                    holder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    Glide.with(context).load(R.drawable.anonomity_icon).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                    holder.tvName.setText("匿名");
                    holder.tvTime.setText(date);
                    holder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }

        } else {
            holder.rlStivckyView.setBackgroundResource(R.color.message_board);
            holder.ivContent.setBackgroundResource(R.color.message_board);
            holder.llCommentBody.setBackgroundResource(R.color.message_board);
            holder.diriver.setBackgroundResource(R.color.message_board_diriver);

            if (position == 1) {
                Glide.with(context).load(headUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                holder.tvName.setText(name);
                holder.tvTime.setText(date);
                holder.itemView.getTag(FIRST_STICKY_VIEW);
            } else {
                if (!TextUtils.equals(boardItem.getId(), ((MessageBoardItem)datas.get(boardPosition - 1)).getId())) {
                    holder.rlStivckyView.setVisibility(View.VISIBLE);
                    Glide.with(context).load(headUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                    holder.tvName.setText(name);
                    holder.tvTime.setText(date);
                    holder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    Glide.with(context).load(headUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.ivHead);
                    holder.tvName.setText(name);
                    holder.tvTime.setText(date);
                    holder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }
        }

        //点击弹出
        holder.tvOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageBoardDialog messageBoardDialog = new MessageBoardDialog(context, presenter,
                        boardItem, boardPosition, userInfo.getId());
                messageBoardDialog.show();
            }
        });

        if (hasComent) {
            holder.boardCommentList.setOnItemClickListener(new boardCommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    MBCommentItem mbCommentItem = mbCommentList.get(position);
                }
            });

            holder.boardCommentList.setDatas(mbCommentList);
            holder.boardCommentList.setVisibility(View.VISIBLE);
            holder.llCommentBody.setVisibility(View.VISIBLE);
        } else {
            holder.boardCommentList.setVisibility(View.GONE);
            holder.llCommentBody.setVisibility(View.GONE);
        }

            //留言的内容
            holder.tvContent.setText(content);


            holder.itemView.setContentDescription( type + "type" + headUrl + "ivHead" + name + "tvName" + date + "tvTime");
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }
}












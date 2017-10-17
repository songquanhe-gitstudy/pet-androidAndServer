package com.song.petLeague.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.widgets.boardCommentListView;

/**
 * Created by song on 2017/4/8.
 */

public class MessageBoardViewHolder extends RecyclerView.ViewHolder {
    public static final int TYPE_TRUENAME = 1;
    public RelativeLayout rlStivckyView;
    public ImageView ivHead;
    public TextView tvName;
    public ImageView ivSex;
    public TextView tvTime;
    public ImageView tvOperation;
    public ImageView ivContent;
    public TextView tvContent;
    public TextView diriver;
    public LinearLayout llCommentBody;
    public boardCommentListView boardCommentList;

    public MessageBoardViewHolder(View itemView) {
        super(itemView);

        rlStivckyView = (RelativeLayout) itemView.findViewById(R.id.rl_stivcky_view);
        ivHead = (ImageView) itemView.findViewById(R.id.iv_head_suspend);
        tvName = (TextView) itemView.findViewById(R.id.tv_name_suspend);
        ivSex = (ImageView) itemView.findViewById(R.id.iv_sex_suspend);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time_suspend);
        tvOperation = (ImageView) itemView.findViewById(R.id.iv_operation_suspend);
        ivContent = (ImageView) itemView.findViewById(R.id.iv_message_board);
        tvContent = (TextView) itemView.findViewById(R.id.tv_board_content);
        diriver = (TextView) itemView.findViewById(R.id.tv_diriver);
        llCommentBody = (LinearLayout) itemView.findViewById(R.id.boardCommentBody);
        boardCommentList = (boardCommentListView) itemView.findViewById(R.id.boardCommentList);

    }
}

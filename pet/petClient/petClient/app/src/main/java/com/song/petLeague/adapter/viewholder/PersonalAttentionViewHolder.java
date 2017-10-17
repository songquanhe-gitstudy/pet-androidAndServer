package com.song.petLeague.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.petLeague.R;

/**
 * Created by song on 2017/4/3.
 */

public class PersonalAttentionViewHolder extends RecyclerView.ViewHolder{

    public ImageView ivHead;
    public TextView tvName;
    public ImageView ivSex;
    public TextView tvAttribute;
    public ImageView ivTogether;

    public PersonalAttentionViewHolder(View itemView) {
        super(itemView);

        ivHead = (ImageView) itemView.findViewById(R.id.iv_head_item);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        ivSex = (ImageView) itemView.findViewById(R.id.im_sex);
        tvAttribute = (TextView) itemView.findViewById(R.id.tv_attribute);
        ivTogether = (ImageView) itemView.findViewById(R.id.im_attention_together);
    }
}

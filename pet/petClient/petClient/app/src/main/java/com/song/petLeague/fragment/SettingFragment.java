package com.song.petLeague.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.song.petLeague.R;
import com.song.petLeague.activity.PersonalAttentionActivity;
import com.song.petLeague.activity.PersonalDetailsActivity;
import com.song.petLeague.activity.PersonalDocumentActivity;
import com.song.petLeague.activity.PersonalFensActivity;
import com.song.petLeague.bean.User;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by song on 2017/3/20.
 */

public class SettingFragment extends Fragment {

    @Bind(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @Bind(R.id.iv_mine_photo)
    ImageView ivMinePhoto;
    @Bind(R.id.tv_mine_name)
    TextView tvMineName;
    @Bind(R.id.tv_mine_college)
    TextView tvMineCollege;
    @Bind(R.id.ll_mine_detail)
    LinearLayout llMineDetail;
    @Bind(R.id.fl_detail_bg)
    FrameLayout flDetailBg;
    @Bind(R.id.ll_mine_attention)
    LinearLayout llMineAttention;
    @Bind(R.id.ll_mine_file_edit)
    LinearLayout llMineFileEdit;
    @Bind(R.id.ll_mine_fens)
    LinearLayout llMineFens;
    @Bind(R.id.layout_dynamic)
    LinearLayout layoutDynamic;
    @Bind(R.id.rl_mine_circle_message)
    RelativeLayout rlMineCircleMessage;
    @Bind(R.id.rl_mine_board_message)
    RelativeLayout rlMineBoardMessage;
    @Bind(R.id.rl_mine_collect)
    RelativeLayout rlMineCollect;
    @Bind(R.id.rl_mine_recommed)
    RelativeLayout rlMineRecommed;
    @Bind(R.id.rl_mine_feedback)
    RelativeLayout rlMineFeedback;
    @Bind(R.id.rl_mine_setting)
    RelativeLayout rlMineSetting;
    private View view;
    private User userInfo;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView ivMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();

        return view;
    }

    private void initView() {
        ivBack = (ImageView) view.findViewById(R.id.iv_title_bar_back);
        ivBack.setVisibility(View.GONE);
        tvTitle = (TextView) view.findViewById(R.id.tv_title_bar);
        tvTitle.setText("我的");
        ivMore = (TextView) view.findViewById(R.id.iv_title_bar_more);
        ivMore.setVisibility(View.GONE);
    }

    private void initData() {
        userInfo = PreUtils.getUserInfo(getActivity());

        Glide.with(this).load(userInfo.getHeadUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).
                placeholder(R.color.bg_no_photo).into(ivMinePhoto);
        tvMineName.setText(userInfo.getName());
        tvMineCollege.setText(userInfo.getuCollege());


    }

    private void initListener() {
        //点击背景进入个人详情
        llMineDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userInfo);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        //点击关注
        llMineAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalAttentionActivity.startPersonAttention(getActivity(), userInfo.getId());
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //点击资料编辑
        llMineFileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalDocumentActivity.startPersonDocumentActivity(getActivity(), userInfo);
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //点击粉丝
        llMineFens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalFensActivity.startPersonalFensActivity(getActivity(), userInfo.getId());
//                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //点击圈子消息
        rlMineCircleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "圈子消息");
            }
        });

        //点击留言板消息
        rlMineBoardMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "留言板消息");
            }
        });

        //点击我的收藏
        rlMineCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "我的收藏");
            }
        });

        //点击推荐给好友
        rlMineRecommed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "推荐给好友");
            }
        });

        //点击反馈
        rlMineFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "反馈");
            }
        });

        //点击设置
        rlMineSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(getActivity(), "设置");
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}



















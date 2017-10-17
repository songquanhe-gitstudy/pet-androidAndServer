package com.song.petLeague.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.song.petLeague.R;
import com.song.petLeague.adapter.ChatAdapter.ConversationListAdapterEx;
import com.song.petLeague.adapter.TabPageIndicatorAdapter;
import com.song.petLeague.fragment.innerFragment.FrientCircleFragment;
import com.song.petLeague.fragment.innerFragment.MyCircleFragment;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by song on 2017/3/20.
 */

public class CircleFragment extends Fragment {
    //标题
    private static final String[] TITLE = new String[]{"宠圈", "关注", "聊天"};
    private static final int[] TYPE_ID = new int[]{1, 2};

    //fragment的集合
    private List<Fragment> fragments = new ArrayList<>();

    private View view;
    private ViewPager circle_viewpager;
    private SegmentTabLayout tabLayout;
    private TabPageIndicatorAdapter adapter;
    private ConversationListFragment mConversationListFragment = null;
    private Conversation.ConversationType[] mConversationsTypes = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle, container, false);
        initView();
        initData();
        setAdapter();
        setListener();
        return view;
    }

    private void initView() {
        circle_viewpager = (ViewPager) view.findViewById(R.id.circle_viewpager);
        tabLayout = (SegmentTabLayout) view.findViewById(R.id.mtablayout);
    }

    private void initData() {
        Fragment conversationList = initConversationList();
        MyCircleFragment myCircle_fragment = new MyCircleFragment();    //我的圈子
        FrientCircleFragment firentCircle_fragment = new FrientCircleFragment();    //朋友的圈子
        fragments.add(myCircle_fragment);
        fragments.add(firentCircle_fragment);
        fragments.add(conversationList);
        tabLayout.setTabData(TITLE);//给Tablayout设置标题

    }

    private void setAdapter() {
        adapter = new TabPageIndicatorAdapter(getFragmentManager(), fragments, TITLE);
        circle_viewpager.setAdapter(adapter);
    }

    private void setListener() {
        //为tablayout设置选中监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                circle_viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        //同时，还要给Viewpager设置选中监听，才能使SegmentTablayout和ViewPager双向同步。
        circle_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                    .build();
            mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.PUBLIC_SERVICE,
                    Conversation.ConversationType.APP_PUBLIC_SERVICE,
                    Conversation.ConversationType.SYSTEM,
                    Conversation.ConversationType.DISCUSSION
            };

            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }


}




















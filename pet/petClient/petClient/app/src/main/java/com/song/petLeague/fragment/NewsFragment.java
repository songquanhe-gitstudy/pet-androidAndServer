package com.song.petLeague.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxz.PagerSlidingTabStrip;
import com.song.petLeague.R;
import com.song.petLeague.adapter.TabPageIndicatorAdapter;
import com.song.petLeague.fragment.innerFragment.CommondFragment;
import com.song.petLeague.fragment.innerFragment.NewsManagerFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by song on 2017/3/20.
 */

public class NewsFragment extends Fragment {

    private static final String[] TITLE = new String[]{"宠物焦点", "宠物锻炼", "宠物常识"};
    private static final int[] TYPE = new int[]{2, 3};
    private PagerSlidingTabStrip tabs;
    List<Fragment> fragments = new ArrayList<>();

    private View view;
    private ViewPager news_viewpager;

    TabPageIndicatorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initVeiw();
        initData();
        initAdapter();
        initListener();
        return view;
    }

    public void initVeiw() {
        news_viewpager = (ViewPager) view.findViewById(R.id.news_viewpager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
    }

    private void initData() {
        NewsManagerFragment newManager_fragment = new NewsManagerFragment();//新闻1
        fragments.add(newManager_fragment);
        for (int i = 0; i < TYPE.length; i++) {
            CommondFragment commond_fragment = new CommondFragment();//其他两个新闻
            Bundle bundle = new Bundle();
            bundle.putInt("type", TYPE[i]);
            commond_fragment.setArguments(bundle);
            fragments.add(commond_fragment);
        }
    }

    private void initAdapter() {
        //实例化适配器
        adapter = new TabPageIndicatorAdapter(getFragmentManager(), fragments, TITLE);
        news_viewpager.setAdapter(adapter);
        tabs.setViewPager(news_viewpager);
    }

    private void initListener() {

    }
}

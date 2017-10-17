package com.song.petLeague.activity.chatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.song.petLeague.R;
import com.song.petLeague.adapter.ChatAdapter.SubConversationListAdapterEx;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.SubConversationListFragment;

/**
 * Created by Bob on 15/11/3.
 * 聚合会话列表
 */
public class SubConversationListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rong);
        SubConversationListFragment fragment = new SubConversationListFragment();
        fragment.setAdapter(new SubConversationListAdapterEx(RongContext.getInstance()));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();

        Intent intent = getIntent();
        if (intent.getData() == null) {
            return;
        }
        //聚合会话参数
        String type = intent.getData().getQueryParameter("type");

        if (type == null)
            return;

        if (type.equals("group")) {
            setTitle("群组");
        } else if (type.equals("private")) {
            setTitle("我的私人会话");
        } else if (type.equals("discussion")) {
            setTitle("我的讨论组");
        } else if (type.equals("system")) {
            setTitle("系统消息");
        } else {
            setTitle("聊天");
        }
    }
}

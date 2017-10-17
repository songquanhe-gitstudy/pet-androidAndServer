package com.song.petLeague;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.song.petLeague.adapter.MainFragmentPageAdapter;
import com.song.petLeague.bean.model.UserInfo;
import com.song.petLeague.fragment.CircleFragment;
import com.song.petLeague.fragment.HealthFragment;
import com.song.petLeague.fragment.NewsFragment;
import com.song.petLeague.fragment.SettingFragment;
import com.song.petLeague.fragment.ShopFragment;
import com.song.petLeague.utils.API;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.utils.ToastUtil;
import com.song.petLeague.widgets.DragPointView;
import com.song.petLeague.widgets.TipsToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.RLog;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.ContactNotificationMessage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

//import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity implements
        ViewPager.OnPageChangeListener,
        DragPointView.OnDragListencer,
        IUnReadMessageObserver, View.OnClickListener{
    private ViewPager mViewPager;
    private List<Fragment> fragemnts = new ArrayList<>();
    private MainFragmentPageAdapter adapter;
    private RadioGroup rgp;
    private TipsToast tipsToast;
    private UserInfo userInfo;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String resultId;
    private DragPointView mUnreadNumView;
    private ImageView mImageChats, mImageNews, mImageShopping, mImageServicer, mImageMe;
    private TextView mTextChats, mTextNews, mTextShopping, mTextServicer, mTextMe;

    //退出时间
    private long exitTime = 0;
//    private IFlytekUpdate mUpdManager;
    private Fragment fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfirst);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        initView();
        changeTextViewColor();
        changeSelectedTabState(0);
        initMainViewPager();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
//        MobclickAgent.onResume(this);
    }

    //初始化控件
    private void initView() {
            RelativeLayout circleLayout = (RelativeLayout) findViewById(R.id.rl_bottom_circle);
            RelativeLayout newsLayout = (RelativeLayout) findViewById(R.id.rl_bottom_news);
            RelativeLayout shoppingLayout = (RelativeLayout) findViewById(R.id.rl_botton_shopping);
//            RelativeLayout servicerLayout = (RelativeLayout) findViewById(R.id.rl_bottom_servicer);
            RelativeLayout meLayout = (RelativeLayout) findViewById(R.id.rl_bottom_me);
            mImageChats = (ImageView) findViewById(R.id.tab_img_chats);
            mImageNews = (ImageView) findViewById(R.id.tab_img_news);
            mImageShopping = (ImageView) findViewById(R.id.tab_img_shopping);
//            mImageServicer = (ImageView) findViewById(R.id.tab_img_servicer);
            mImageMe = (ImageView) findViewById(R.id.tab_img_me);
            mTextChats = (TextView) findViewById(R.id.tab_text_chats);
            mTextNews = (TextView) findViewById(R.id.tab_text_news);
            mTextShopping = (TextView) findViewById(R.id.tab_text_shopping);
//            mTextServicer = (TextView) findViewById(R.id.tab_text_servicer);
            mTextMe = (TextView) findViewById(R.id.tab_text_me);

            circleLayout.setOnClickListener(this);
            newsLayout.setOnClickListener(this);
            shoppingLayout.setOnClickListener(this);
//            servicerLayout.setOnClickListener(this);
            meLayout.setOnClickListener(this);

    }

    private void initMainViewPager() {
        postId(PreUtils.getString("id", this));
        //添加五大模块
        CircleFragment circle_fragemnt = new CircleFragment();
        NewsFragment news_Fragment = new NewsFragment();
        ShopFragment shop_Fragment = new ShopFragment();
        HealthFragment health_Fragment = new HealthFragment();
        SettingFragment setting_Fragment = new SettingFragment();
        fragemnts.add(circle_fragemnt);//校圈
        fragemnts.add(news_Fragment);//新闻
        fragemnts.add(shop_Fragment);//商城
//        fragemnts.add(health_Fragment);//健康
        fragemnts.add(setting_Fragment);//设置
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragemnts.get(position);
            }

            @Override
            public int getCount() {
                return fragemnts.size();
            }
        };

        //消息数量
        mUnreadNumView = (DragPointView) findViewById(R.id.seal_num_circle);
        mUnreadNumView.setOnClickListener(this);
        mUnreadNumView.setDragListencer(this);

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        //设置viewpager的预加载页数，viewpager默认只会预加载左右两边的页面数据

        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0, false);
    }

    //初始化数据
    private void initData() {
        final Conversation.ConversationType[] conversationTypes = {
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE
        };

        //未读消息
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);

        /*//用户头像
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public io.rong.imlib.model.UserInfo getUserInfo(String userId) {

                    return new io.rong.imlib.model.UserInfo(userId, "宋泉柯", Uri.parse("http://192.168.191.1:8080/petServer/upload/head_bg.jpg"));
            }
        }, true);*/
        getConversationPush();// 获取 push 的 id 和 target
        getPushMessage();
        //刷新用户
        RongIM.getInstance().refreshUserInfoCache(new io.rong.imlib.model.UserInfo(PreUtils.getString("id", MainActivity.this),
                PreUtils.getString("username", MainActivity.this),
                Uri.parse(PreUtils.getString("headUrl", MainActivity.this))));
    }


    private void getConversationPush() {
        if (getIntent() != null && getIntent().hasExtra("PUSH_CONVERSATIONTYPE") && getIntent().hasExtra("PUSH_TARGETID")) {

            final String conversationType = getIntent().getStringExtra("PUSH_CONVERSATIONTYPE");
            final String targetId = getIntent().getStringExtra("PUSH_TARGETID");


            RongIM.getInstance().getConversation(Conversation.ConversationType.valueOf(conversationType), targetId, new RongIMClient.ResultCallback<Conversation>() {
                @Override
                public void onSuccess(Conversation conversation) {

                    if (conversation != null) {
                        if (conversation.getLatestMessage() instanceof ContactNotificationMessage) { //好友消息的push
//                            startActivity(new Intent(MainActivity.this, NewFriendListActivity.class));

                        } else {
                            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversation")
                                    .appendPath(conversationType).appendQueryParameter("targetId", targetId).build();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode e) {

                }
            });
        }
    }

    /**
     * 得到不落地 push 消息
     */
    private void getPushMessage() {
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            String path = intent.getData().getPath();
            if (path.contains("push_message")) {
                SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
                String cacheToken = sharedPreferences.getString("loginToken", "");
                if (TextUtils.isEmpty(cacheToken)) {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    if (!RongIM.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
                        RongIM.connect(cacheToken, new RongIMClient.ConnectCallback() {
                            @Override
                            public void onTokenIncorrect() {

                            }

                            @Override
                            public void onSuccess(String s) {

                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode e) {
                            }
                        });
                    }
                }
            }
        }
    }

    private void postId(final String id) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("id", id)

                .build();
        Request request = new Request.Builder().post(body).url(API.GET_TOKEN).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e);
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                userInfo = JSON.parseObject(str, UserInfo.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connect();
                    }
                });
            }
        });
    }

    private void connect() {
        if (userInfo != null) {
            if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
                RongIM.connect(userInfo.getToken(), new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {
                    }
                    @Override
                    public void onSuccess(String userid) {
                        resultId = userid;
                        //userid，是我们在申请token时填入的userid
                        System.out.println("========userid" + userid);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editor.putString("loginToken", userInfo.getToken());
                            }
                        });
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTextViewColor();
        changeSelectedTabState(position);
    }

    private void changeTextViewColor() {
        mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle));
        mImageNews.setBackgroundDrawable(getResources().getDrawable(R.drawable.news));
        mImageShopping.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopping));
//        mImageServicer.setBackgroundDrawable(getResources().getDrawable(R.drawable.healthy));
        mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.setting));

        mTextChats.setTextColor(Color.parseColor("#BDBDBD"));
        mTextNews.setTextColor(Color.parseColor("#BDBDBD"));
        mTextShopping.setTextColor(Color.parseColor("#BDBDBD"));
//        mTextServicer.setTextColor(Color.parseColor("#BDBDBD"));
        mTextMe.setTextColor(Color.parseColor("#BDBDBD"));
    }

    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextChats.setTextColor(Color.parseColor("#D33A31"));
                mImageChats.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_press));
                break;
            case 1:
                mTextNews.setTextColor(Color.parseColor("#D33A31"));
                mImageNews.setBackgroundDrawable(getResources().getDrawable(R.drawable.news_press));
                break;
            case 2:
                mTextShopping.setTextColor(Color.parseColor("#D33A31"));
                mImageShopping.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopping_press));
                break;
            /*case 3:
                mTextServicer.setTextColor(Color.parseColor("#D33A31"));
                mImageServicer.setBackgroundDrawable(getResources().getDrawable(R.drawable.healthy_press));
                break;*/
            case 3:
                mTextMe.setTextColor(Color.parseColor("#D33A31"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.drawable.setting_press));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDragOut() {
        mUnreadNumView.setVisibility(View.GONE);
        ToastUtil.showShort(this, "清理成功");
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations != null && conversations.size() > 0) {
                    for (Conversation c : conversations) {
                        RongIM.getInstance().clearMessagesUnreadStatus(c.getConversationType(), c.getTargetId(), null);
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {

            }
        }, Conversation.ConversationType.PRIVATE);

    }

    @Override
    public void onCountChanged(int count) {
        if (count == 0) {
            mUnreadNumView.setVisibility(View.GONE);
        } else if (count > 0 && count < 100) {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText(String.valueOf(count));
        } else {
            mUnreadNumView.setVisibility(View.VISIBLE);
            mUnreadNumView.setText("...");
        }
    }

    long firstClick = 0;
    long secondClick = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bottom_circle:
                if (mViewPager.getCurrentItem() == 0) {
                    if (firstClick == 0) {
                        firstClick = System.currentTimeMillis();
                    } else {
                        secondClick = System.currentTimeMillis();
                    }
                    RLog.i("MainActivity", "time = " + (secondClick - firstClick));
                    if (secondClick - firstClick > 0 && secondClick - firstClick <= 800) {
//                        mConversationListFragment.focusUnreadItem();
                        firstClick = 0;
                        secondClick = 0;
                    } else if (firstClick != 0 && secondClick != 0) {
                        firstClick = 0;
                        secondClick = 0;
                    }
                }
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.rl_bottom_news:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.rl_botton_shopping:
                mViewPager.setCurrentItem(2, false);
                break;
            /*case R.id.rl_bottom_servicer:
                mViewPager.setCurrentItem(3, false);
                break;*/
            case R.id.rl_bottom_me:
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    /**
     * 按两次退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showTips(R.drawable.tips_smile, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 自定义toast
     *
     * @param iconResId 图片
     * @param tips      提示文字
     */
    private void showTips(int iconResId, String tips) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast.cancel();
            }
        } else {
            tipsToast = TipsToast.makeText(MainActivity.this.getApplication()
                    .getBaseContext(), tips, TipsToast.LENGTH_SHORT);
        }
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(tips);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        //此处解决有时候出现getActivity（）出现null的情况
    }

    //从登陆界面跳转过来
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}

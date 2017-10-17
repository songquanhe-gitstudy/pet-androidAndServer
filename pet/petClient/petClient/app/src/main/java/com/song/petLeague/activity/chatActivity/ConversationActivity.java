package com.song.petLeague.activity.chatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.fragment.chatFragment.ConversationFragmentEx;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.UriFragment;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * 会话页面
 * 1，设置 ActionBar title
 * 2，加载会话页面
 * 3，push 和 通知 判断
 */
public class ConversationActivity extends FragmentActivity implements View.OnClickListener {

    private String TAG = ConversationActivity.class.getSimpleName();
    /**
     * 对方id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    /**
     * title
     */
    private String title;
    /**
     * 是否在讨论组内，如果不在讨论组内，则进入不到讨论组设置页面
     */
    private boolean isFromPush = false;

    private Button btnBack;
    private Button btnRight;
    private TextView tvTitle;

    private SharedPreferences sp;

    private final String TextTypingTitle = "对方正在输入...";
    private final String VoiceTypingTitle = "对方正在讲话...";

    private Handler mHandler;

    public static final int SET_TEXT_TYPING_TITLE = 1;
    public static final int SET_VOICE_TYPING_TITLE = 2;
    public static final int SET_TARGET_ID_TITLE = 0;


    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        sp = getSharedPreferences("config", MODE_PRIVATE);

        btnBack = (Button) findViewById(R.id.btn_left);
        btnBack.setOnClickListener(this);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnRight.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);


        Intent intent = getIntent();

        if (intent == null || intent.getData() == null)
            return;

        mTargetId = intent.getData().getQueryParameter("targetId");

        mConversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));

        title = intent.getData().getQueryParameter("title");

        setActionBarTitle(mConversationType, mTargetId);

        if (mConversationType.equals(Conversation.ConversationType.PRIVATE) | mConversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE) | mConversationType.equals(Conversation.ConversationType.DISCUSSION)) {
            btnRight.setBackground(getResources().getDrawable(R.drawable.icon1_menu));
        }

        isPushMessage(intent);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case SET_TEXT_TYPING_TITLE:
                        tvTitle.setText(TextTypingTitle);
                        break;
                    case SET_VOICE_TYPING_TITLE:
                        tvTitle.setText(VoiceTypingTitle);
                        break;
                    case SET_TARGET_ID_TITLE:
                        setActionBarTitle(mConversationType, mTargetId);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
                if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
                    int count = typingStatusSet.size();
                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                    if (count > 0) {
                        Iterator iterator = typingStatusSet.iterator();
                        TypingStatus status = (TypingStatus) iterator.next();
                        String objectName = status.getTypingContentType();

                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                        //匹配对方正在输入的是文本消息还是语音消息
                        if (objectName.equals(textTag.value())) {
                            mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
                        } else if (objectName.equals(voiceTag.value())) {
                            mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
                        }
                    } else {//当前会话没有用户正在输入，标题栏仍显示原来标题
                        mHandler.sendEmptyMessage(SET_TARGET_ID_TITLE);
                    }
                }
            }
        });

    }

    /**
     * 判断是否是 Push 消息，判断是否需要做 connect 操作
     */
    private void isPushMessage(Intent intent) {

        if (intent == null || intent.getData() == null)
            return;
                enterFragment(mConversationType, mTargetId);
        }

    private ConversationFragmentEx fragment;

    /**
     * 加载会话页面 ConversationFragmentEx 继承自 ConversationFragment
     *
     * @param mConversationType 会话类型
     * @param mTargetId         会话 Id
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        fragment = new ConversationFragmentEx();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.rong_content, fragment);
        transaction.commitAllowingStateLoss();
    }


    /**
     * 设置会话页面 Title
     *
     * @param conversationType 会话类型
     * @param targetId         目标 Id
     */
    private void setActionBarTitle(Conversation.ConversationType conversationType, String targetId) {

        if (conversationType == null)
            return;

        if (conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            setPrivateActionBar(targetId);
        }  else if (conversationType.equals(Conversation.ConversationType.SYSTEM)) {
            tvTitle.setText("系统消息");
        } else {
            setTitle("聊天");
        }
    }

    /**
     * 设置私聊界面 ActionBar
     */
    private void setPrivateActionBar(String targetId) {
        if (!TextUtils.isEmpty(title)) {
            if (title.equals("null")) {
                if (!TextUtils.isEmpty(targetId)) {
                    UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(targetId);
                    if (userInfo != null) {
                        tvTitle.setText(userInfo.getName());
                    }
                }
            } else {
                tvTitle.setText(title);
            }

        } else {
            tvTitle.setText(targetId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                if (mConversationType == Conversation.ConversationType.PUBLIC_SERVICE
                        || mConversationType == Conversation.ConversationType.APP_PUBLIC_SERVICE) {

                    RongIM.getInstance().startPublicServiceProfile(this, mConversationType, mTargetId);
                } else {
                    UriFragment fragment = (UriFragment) getSupportFragmentManager().getFragments().get(0);
                    //得到讨论组的 targetId
                    mTargetId = fragment.getUri().getQueryParameter("targetId");

                    Intent intent = null;
                    if (mConversationType == Conversation.ConversationType.PRIVATE) {
//                intent = new Intent(this, PrivateChatDetailActivity.class);
                        intent.putExtra("conversationType", Conversation.ConversationType.PRIVATE);
                    }
                    intent.putExtra("TargetId", mTargetId);
                    if (intent != null) {
                        startActivityForResult(intent, 500);
                    }

                }
                break;

            case R.id.btn_left:
                finish();
        }
    }
}

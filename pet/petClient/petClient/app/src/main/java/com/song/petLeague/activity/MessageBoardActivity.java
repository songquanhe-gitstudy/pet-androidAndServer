package com.song.petLeague.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.song.petLeague.R;
import com.song.petLeague.adapter.MessageBoarcAdapter;
import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.contract.MessageBoardContract;
import com.song.petLeague.mvp.presenter.MessageBoardPresenter;
import com.song.petLeague.utils.CommonUtils;
import com.song.petLeague.utils.GlideCircleTransform;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.widgets.DivItemDecoration;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/4/8.
 */

public class MessageBoardActivity extends Activity implements MessageBoardContract.View, View.OnClickListener{

    private static final int TYPE_PULLREFRESH = 1;
    private static final int TYPE_UPLOADREFRESH = 2;
    private static final String OPEN_BOARD = "1";
    private static final String ANONIYMITY_BOARD = "2";
    private MessageBoardPresenter presenter;
    private User userInfo;
    private String mId;

    private SuperRecyclerView recyclerView;
    private ImageView ivBack;
    private RelativeLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;

    private FloatingActionMenu menuRed;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;

    private RelativeLayout rlStickyView;
    private TextView tvName;
    private TextView tvTime;
    private ImageView ivHead;
    private SwipeRefreshLayout.OnRefreshListener refershListener;
    private MessageBoarcAdapter messageBoardAdapter;
    private int currentPage = 1;
    private String editeTextUid = null;
    private String editTextMsid = null;
    private int editTextBoardPosition;

    public static void startMessageBoardActivity(Context context, String uId) {
        Intent intent = new Intent(context, MessageBoardActivity.class);
        intent.putExtra("uId", uId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);
        presenter = new MessageBoardPresenter(this);
        initView();
        initIntentData();
        initData();
        initAdapter();
        initListener();
        initRefresh();
    }

    private void initRefresh() {
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);
                refershListener.onRefresh();
                recyclerView.setRefreshingColorResources(R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red);
            }
        });
    }

    private void initView() {
        recyclerView = (SuperRecyclerView) findViewById(R.id.message_board_recyclerView);
        rlStickyView = (RelativeLayout) findViewById(R.id.rl_stivcky_view);

        //评论栏
        edittextbody = (RelativeLayout) findViewById(R.id.editTextBodyLl);
        editText = (EditText) findViewById(R.id.circleEt);
        sendIv = (ImageView) findViewById(R.id.sendIv);

        ivBack = (ImageView) findViewById(R.id.iv_back_board);
        ivHead = (ImageView) findViewById(R.id.iv_head_suspend);
        tvName = (TextView) findViewById(R.id.tv_name_suspend);
        tvTime = (TextView) findViewById(R.id.tv_time_suspend);

        //留言入口
        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(this);
        menuRed.setClosedOnTouchOutside(true);
        menuRed.showMenuButton(true);
    }

    private void initIntentData() {
        userInfo = PreUtils.getUserInfo(this);
        mId = getIntent().getStringExtra("uId");
    }

    private void initData() {
        rlStickyView.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(40, true));
        messageBoardAdapter = new MessageBoarcAdapter(this);
        messageBoardAdapter.setMessageBoardPresener(presenter);
        recyclerView.setAdapter(messageBoardAdapter);

    }

    private void initAdapter() {

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                View currentView = ((LinearLayoutManager) recyclerView.getLayoutManager()).findViewByPosition(firstVisibleItemPosition);
                int firstItemHeight = currentView.getHeight();
                totalDy += dy;
                Log.e("dd", "totalDy: " + totalDy );

                if (totalDy>=(firstItemHeight)) {
                    rlStickyView.setVisibility(View.VISIBLE);
                } else {
                    rlStickyView.setVisibility(View.GONE);
                }

                View stickyInfoView = recyclerView.findChildViewUnder(
                        rlStickyView.getMeasuredWidth() / 2, 5);
                //tvStickyHeaderView.getMeasuredWidth() / 2 : 720
                //显示在顶端的数据
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    String boardItemString = String.valueOf(stickyInfoView.getContentDescription());
                    String[] types = boardItemString.split("type");
                    if (types[0].equals("1")) {
                        String[] ivHeads = types[1].split("ivHead");
                        String[] tvNames = ivHeads[1].split("tvName");
                        String[] tvTimes = tvNames[1].split("tvTime");
                        Glide.with(MessageBoardActivity.this).load(ivHeads[0]).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo)
                                .transform(new GlideCircleTransform(MessageBoardActivity.this)).into(ivHead);
                        tvName.setText(tvNames[0]);
                        tvTime.setText(tvTimes[0]);
                        rlStickyView.setBackgroundResource(R.color.message_board);
                    } else {
                        String[] ivHeads = types[1].split("ivHead");
                        String[] tvNames = ivHeads[1].split("tvName");
                        String[] tvTimes = tvNames[1].split("tvTime");
                        Glide.with(MessageBoardActivity.this).load(R.drawable.anonomity_icon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo)
                                .transform(new GlideCircleTransform(MessageBoardActivity.this)).into(ivHead);
                        tvTime.setText(tvTimes[0]);
                        tvName.setText("匿名");
                        rlStickyView.setBackgroundResource(R.color.bar_grey);
                    }



                }

                //找到指定的view
                View transInfoView = recyclerView.findChildViewUnder(
                        rlStickyView.getMeasuredWidth() / 2, rlStickyView.getMeasuredHeight() + 1);
                //tvStickyHeaderView.getMeasuredHeight() + 1: 1561

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - rlStickyView.getMeasuredHeight();
                    Log.e("1", "dealtY: " + dealtY + ",  transInfoView.getTop(): " + transInfoView.getTop() +
                            ", rlStickyView.getMeasuredHeight()" + rlStickyView.getMeasuredHeight());
                    if (transViewStatus == MessageBoarcAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            rlStickyView.setTranslationY(dealtY);
                        } else {
                            rlStickyView.setTranslationY(0);
                        }
                    } else if (transViewStatus == MessageBoarcAdapter.NONE_STICKY_VIEW) {
                        rlStickyView.setTranslationY(0);
                    }
                }
            }
        });

        refershListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("MessageBoard", "run:---下拉刷新 ");
                        currentPage = 1;
                        messageBoardAdapter.RemoveAll();
                        presenter.loadData(TYPE_PULLREFRESH, currentPage, userInfo.getId(), mId);
                    }
                }, 1000);
            }
        };
        recyclerView.setRefreshListener(refershListener);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        //触摸事件
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updata2showEditTextBodyVisiable(View.GONE, null, null, -1);
                    return true;
                }
                return false;
            }
        });

        //发送评论点击事件
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    String content =  editText.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(MessageBoardActivity.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addBoardComment(content, editeTextUid, editTextMsid, editTextBoardPosition);
                }
                updata2showEditTextBodyVisiable(View.GONE, null, null, -1);
            }
        });

        //点击编辑按钮
        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                }
                menuRed.toggle(true);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void updata2LoadData(int refreshType, final List<MessageBoardItem> boardItemList) {
        if (refreshType == TYPE_PULLREFRESH){
            Log.e(TAG, "update2loadData1: " + boardItemList.size() );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setRefreshing(false);
                    messageBoardAdapter.setDatas(boardItemList);
                }
            });

        }else if(refreshType == TYPE_UPLOADREFRESH){
            Log.e(TAG, "update2loadData2: " + boardItemList.size() );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.hideMoreProgress();
                    messageBoardAdapter.setDatas(boardItemList);
                }
            });


        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageBoardAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void updata2DeleteMessageBoard(int boadPosition) {
        messageBoardAdapter.getDatas().remove(boadPosition);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageBoardAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void updata2showEditTextBodyVisiable(int visibility, String uId, String msId, int boardPosition) {
        editeTextUid = uId;
        editTextMsid = msId;
        editTextBoardPosition = boardPosition;
        edittextbody.setVisibility(visibility);
        if(View.VISIBLE==visibility){
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput( editText.getContext(),  editText);
            menuRed.showMenuButton(false);
        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( editText.getContext(),  editText);
            menuRed.showMenuButton(true);
        }
    }

    @Override
    public void updata2AddBoardComment(int boardPosition, MBCommentItem mbCommentItem) {
        if (mbCommentItem != null) {
            MessageBoardItem messageBoardItem = (MessageBoardItem) messageBoardAdapter.getDatas().get(boardPosition);
            messageBoardItem.getMBCommentList().add(mbCommentItem);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageBoardAdapter.notifyDataSetChanged();
                }
            });

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText.setText("");
                }
            });
        }

    }

    @Override
    public void updata2AddMessageBoard() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab1:
                Intent publicBoardIntent = new Intent(this, EditMessageBoardActivity.class);
                publicBoardIntent.putExtra("BOARD_TYPE", OPEN_BOARD);
                publicBoardIntent.putExtra("umId", mId);
                startActivity(publicBoardIntent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                menuRed.toggle(false);
                break;
            case R.id.fab2:
                Intent anonymityBoardIntent = new Intent(this, EditMessageBoardActivity.class);
                anonymityBoardIntent.putExtra("BOARD_TYPE", ANONIYMITY_BOARD);
                anonymityBoardIntent.putExtra("umId", mId);
                startActivity(anonymityBoardIntent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                menuRed.toggle(false);
                break;
        }
    }
}


























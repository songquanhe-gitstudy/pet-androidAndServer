package com.song.petLeague.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.song.petLeague.R;
import com.song.petLeague.adapter.PersonalAttentionAdapter;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.contract.PersonalAttentionContract;
import com.song.petLeague.mvp.presenter.PersonAttenionPresenter;
import com.song.petLeague.utils.ToastUtil;
import com.song.petLeague.widgets.DivItemDecoration;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/4/3.
 */

public class PersonalAttentionActivity extends Activity implements PersonalAttentionContract.View{

    private static final int TYPE_PULLREFRESH = 1;
    private static final int TYPE_UPLOADREFRESH = 2;
    private SuperRecyclerView recyclerView;
    private PersonAttenionPresenter presenter;
    private PersonalAttentionAdapter personalAdapter;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private String uId;

    private ImageView ivBack;
    private ImageView ivMore;
    private TextView TvTitle;

    private int currentPage = 1;


    public static void startPersonAttention(Context context, String uId) {
        Intent intent = new Intent(context, PersonalAttentionActivity.class);
        intent.putExtra("uId", uId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_attention);
        presenter = new PersonAttenionPresenter(this);
        initView();
        initIntentData();
        initData();
        initAdapter();
        initListener();
        initRefresh(); //实现自动刷新
    }

    private void initRefresh() {
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);
                refreshListener.onRefresh();
                recyclerView.setRefreshingColorResources(R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red);
            }
        });
    }

    private void initView() {

        recyclerView = (SuperRecyclerView) findViewById(R.id.per_atten_recyclerView);
        ivBack = (ImageView) findViewById(R.id.iv_back_attention_item);
        ivMore = (ImageView) findViewById(R.id.iv_more_attention_item);
        TvTitle = (TextView) findViewById(R.id.tv_personal_title);
    }

    private void initIntentData() {
        uId = getIntent().getStringExtra("uId");
    }

    private void initData() {
        TvTitle.setText("关注的人");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DivItemDecoration(2, false));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        personalAdapter = new PersonalAttentionAdapter(this);
        personalAdapter.setPresenter(presenter);
        recyclerView.setAdapter(personalAdapter);

    }

    private void initAdapter() {

    }

    private void initListener() {

        //下拉刷新
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: ----下拉刷新");
                        currentPage = 1;
                        personalAdapter.RemoveAll();
                        presenter.loadData(TYPE_PULLREFRESH, currentPage, uId);
                    }
                }, 1000);
            }
        };

        recyclerView.setRefreshListener(refreshListener);
        /*recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });*/

        //上拉刷新
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage++;
                        presenter.loadData(TYPE_UPLOADREFRESH, currentPage, uId);
                    }
                }, 1000);
            }
        }, 1);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });


        //点击事件
        personalAdapter.setOnItemClickListener(new PersonalAttentionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<User> datas = personalAdapter.getDatas();
                User user = datas.get(position);
                Intent intent = new Intent(PersonalAttentionActivity.this, PersonalDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        if(presenter != null) {
            presenter.recycle();
        }
        super.onDestroy();

    }

    //加载传过来的用户数据
    @Override
    public void updata2LoadData(int type,final List<User> userList) {
        if(type == TYPE_PULLREFRESH) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    recyclerView.setRefreshing(false);
                    personalAdapter.setDatas(userList);
                }
            });


        }else if(type == TYPE_UPLOADREFRESH){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.hideMoreProgress();
                    personalAdapter.setDatas(userList);
                }
            });
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personalAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void updata2LoadFensData(int type, List<User> users) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        return super.onKeyDown(keyCode, event);

    }
}

package com.song.petLeague.fragment.innerFragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.software.shell.fab.FloatingActionButton;
import com.song.petLeague.MainActivity;
import com.song.petLeague.R;
import com.song.petLeague.adapter.CircleAdapter;
import com.song.petLeague.adapter.FriendCicleAdapter;
import com.song.petLeague.bean.CircleItem;
import com.song.petLeague.bean.CommentConfig;
import com.song.petLeague.bean.CommentItem;
import com.song.petLeague.bean.FavortItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.contract.CircleContract;
import com.song.petLeague.mvp.presenter.CirclePresenter;
import com.song.petLeague.utils.CommonUtils;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.widgets.CommentListView;
import com.song.petLeague.widgets.DivItemDecoration;

import java.util.List;

/**
 *
 * @ClassName: MainActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 *
 */
public class FrientCircleFragment extends Fragment implements CircleContract.View{

    protected static final String TAG = MainActivity.class.getSimpleName();
    private static final int TYPE_REQUEST = 2;
    private FriendCicleAdapter friendCircleAdapter;
    private RelativeLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private ImageView ivHead, ivBg;

    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;
    private int selectCircleItemH;
    private int selectCommentItemOffset;

    private CirclePresenter presenter;
    private CommentConfig commentConfig;
    private SuperRecyclerView recyclerView;
    private RelativeLayout bodyLayout;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton  actionButton;

    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private int currentPage = 1;

    private View view;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main, container, false);
        presenter = new CirclePresenter(this, getActivity());
        initView();
        initData();
        initListener();
        initRefresh();  //实现自动下拉刷新功能
        return view;
    }

    public void initRefresh() {
        recyclerView.getSwipeToRefresh().post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作de
                recyclerView.setRefreshingColorResources(R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red,R.color.title_bar_red);
            }
        });
    }

    private void initView() {

        View inflateView = View.inflate(getActivity(), R.layout.head_circle, null);
        ivHead = (ImageView) inflateView.findViewById(R.id.icon_head_circle);
        ivBg = (ImageView) inflateView.findViewById(R.id.icon_circle_rebg);
        //帖子编辑栏
        actionButton = (FloatingActionButton) view.findViewById(R.id.action_button);
        actionButton.setVisibility(View.GONE);
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.recyclerView);
        //评论栏
        edittextbody = (RelativeLayout) view.findViewById(R.id.editTextBodyLl);
        editText = (EditText) view.findViewById(R.id.circleEt);
        sendIv = (ImageView) view.findViewById(R.id.sendIv);

        //设置输入框的高度
        setViewTreeObserver();
    }

    private void initData() {

        user = PreUtils.getUserInfo(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true)); //custom driver
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        friendCircleAdapter = new FriendCicleAdapter(getActivity());
        friendCircleAdapter.setCirclePresenter(presenter, TYPE_REQUEST);
        recyclerView.setAdapter(friendCircleAdapter);
    }

    private void initListener() {
        //触摸事件
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        //下拉刷新
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: ----下拉刷新");
                        currentPage = 1;
                        friendCircleAdapter.RemoveAll();
                        presenter.loadData(TYPE_PULLREFRESH, currentPage, TYPE_REQUEST, user.getId());
                    }
                }, 2000);
                //获取用户信息
            }
        };
        recyclerView.setRefreshListener(refreshListener);

        //滚动事件
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                }else {
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                    Glide.with(getActivity()).resumeRequests();
                }else{
//                    Glide.with(getActivity()).pauseRequests();
                }

            }
        });

        //上拉加载更多
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage ++;
                        Log.e(TAG, "onMoreAsked : " + currentPage);
                        presenter.loadData(TYPE_UPLOADREFRESH, currentPage, TYPE_REQUEST, user.getId());
                    }
                }, 2000);

            }
        }, 2);

        //发送评论点击事件
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    String content =  editText.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        Toast.makeText(getActivity(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addComment(content, commentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });

    }

    //清除外部对象的引用，防止内存溢出
    @Override
    public void onDestroy() {
        if(presenter !=null){
            presenter.recycle();
        }
        super.onDestroy();
    }

    //设置输入框的高度
    private void setViewTreeObserver() {
        bodyLayout = (RelativeLayout) view.findViewById(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH =  getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if(r.top != statusBarH ){
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                Log.d(TAG, "screenH＝ "+ screenH +" &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();
                Log.e(TAG, "评论栏的高度: " +  editTextBodyHeight);
                if(keyboardH<150){//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if(layoutManager!=null && commentConfig != null){
                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(commentConfig));
                }
            }
        });
    }

    // 获取状态栏高度
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            Log.e(TAG, "状态栏的高度: " + result);
        }
        return result;
    }

    //实现p中方法根据id删除帖子
    @Override
    public void update2DeleteCircle(String circleId, final int circlePosition) {
		/*List<CircleItem> circleItems = circleAdapter.getDatas();
		for(int i=0; i<circleItems.size(); i++){
			if(circleId.equals(circleItems.get(i).getId())){
				circleItems.remove(i);*/
        friendCircleAdapter.Remove(circlePosition);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendCircleAdapter.notifyItemRemoved(circlePosition + 1);
            }
        });

        //circleAdapter.notifyItemRemoved(i+1);
        return;
    }

    //实现p中方法的点赞
    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        if(addItem != null){
            //找到指定的
            CircleItem item = (CircleItem) friendCircleAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    friendCircleAdapter.notifyDataSetChanged();
                }
            });

            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
    }

    //实现p中方法取消点赞
    @Override
    public void update2DeleteFavort(final int CirclePosition, String favortUserId) {
        CircleItem item = (CircleItem) friendCircleAdapter.getDatas().get(CirclePosition);
        List<FavortItem> items = item.getFavorters();
        for (int i = 0; i < items.size(); i++) {
            if (favortUserId.equals(items.get(i).getUser().getId())) {
                items.remove(i);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        friendCircleAdapter.notifyDataSetChanged();
                    }
                });
				/*runOnUiThread(new Runnable() {
					@Override
					public void run() {
						circleAdapter.notifyItemChanged(mCirclePosition + 1);
						Log.e(TAG, "run: 123");
					}
				});*/

                return;
            }
        }
    }

    //显示所有的帖子数据
    @Override
    public void update2loadData(int loadType, final List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH){
            Log.e(TAG, "update2loadData1: " + datas.size() );
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setRefreshing(false);
                    friendCircleAdapter.setDatas(datas);
                    ivHead.setVisibility(View.GONE);
                    ivBg.setVisibility(View.VISIBLE);
                }
            });

        }else if(loadType == TYPE_UPLOADREFRESH){
            Log.e(TAG, "update2loadData2: " + datas.size() );
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.hideMoreProgress();
                    friendCircleAdapter.setDatas(datas);
                    ivHead.setVisibility(View.GONE);
                    ivBg.setVisibility(View.VISIBLE);
                }
            });


        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendCircleAdapter.notifyDataSetChanged();
            }
        });
    }

    //实现p中方法进行评论
    @Override
    public void update2AddComment(int circlePosition, CommentItem commentItem) {
        if(commentItem != null){
            CircleItem item = (CircleItem) friendCircleAdapter.getDatas().get(circlePosition);
            item.getComments().add(commentItem);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    friendCircleAdapter.notifyDataSetChanged();
                }
            });

            //circleAdapter.notifyItemChanged(circlePosition+1);
        }
        //清空评论文本
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText("");
            }
        });

    }

    //实现p中方法删除评论
    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) friendCircleAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for(int i=0; i<items.size(); i++){
            if(commentId.equals(items.get(i).getId())){
                items.remove(i);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        friendCircleAdapter.notifyDataSetChanged();
                    }
                });

                //circleAdapter.notifyItemChanged(circlePosition+1);
                return;
            }
        }
    }

    //软键盘弹出收回操作实现
    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if(View.VISIBLE==visibility){
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput( editText.getContext(),  editText);

        }else if(View.GONE==visibility){
            //隐藏键盘
            CommonUtils.hideSoftInput( editText.getContext(),  editText);
        }
    }


    //测量偏移量
    private int getListviewOffset(CommentConfig commentConfig) {
        if(commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight ;
        Log.i(TAG, "listviewOffset--1 : " + listviewOffset);
        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        Log.i(TAG, "listviewOffset--2 : " + listviewOffset);
        return listviewOffset;
    }

    //测量帖子高度和评论偏移量
    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        Log.e(TAG, "firstPosition: " + firstPosition);
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);
        Log.e(TAG, "selectCircleItem  : " + selectCircleItem);
        if(selectCircleItem != null){
            selectCircleItemH = selectCircleItem.getHeight();
            Log.e(TAG, "selectCircleItemH  : " + selectCircleItemH);
        }

        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if(commentLv!=null){
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if(selectCommentItem != null){
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if(parentView != null){
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

}

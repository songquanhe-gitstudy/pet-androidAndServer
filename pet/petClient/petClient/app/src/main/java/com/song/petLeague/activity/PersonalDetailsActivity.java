package com.song.petLeague.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.song.petLeague.R;
import com.song.petLeague.bean.PhotoInfo;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.contract.PersonalDetailsContract;
import com.song.petLeague.mvp.presenter.PersonalDetailsPresenter;
import com.song.petLeague.utils.GlideCircleTransform;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.utils.ToastUtil;
import com.song.petLeague.widgets.BlurTransformation;
import com.song.petLeague.widgets.MultiImageView;
import com.song.petLeague.widgets.TipsToast;
import com.song.petLeague.widgets.custom.HeaderZoomLayout;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import static android.content.ContentValues.TAG;
import static com.luck.picture.lib.model.LocalMediaLoader.TYPE_IMAGE;

/**
 * Created by song on 2017/3/30.
 */

public class PersonalDetailsActivity extends Activity implements PersonalDetailsContract.View {

    private LinearLayout llCopyFavotite;
    private LinearLayout llBoardDetail;
    private LinearLayout llCopyDocument;
    private HeaderZoomLayout zommScroll;
    private RelativeLayout reLowHurdle;
    private RelativeLayout rlPerDetail;
    private RelativeLayout rlMineContent;
    private FrameLayout flBg;
    private TextView tvTitle;
    private MultiImageView multiImageView;
    private ImageView ivBack;
    private ImageView ivBg;
    private ImageView ivHeadPic;
    private TextView txtUserName;
    private ImageView ivSex;
    private Button btnAttention;
    private Button btnChat;
    private LinearLayout llAttention;
    private TextView tvAttenCounts, tvCircleCounts;
    private LinearLayout llFens;
    private TextView tvfensCounts;
    private LinearLayout llFavorite;
    private LinearLayout llMessageBoard;
    private LinearLayout llDocument;
    private LinearLayout llMycircle;

    private Context context;
    private PersonalDetailsPresenter personalPresenter;
    private User user;
    private User meUser;
    public static final int HEADVIEW_SIZE = 1;

    private boolean attention;

    private TipsToast tipsToast;
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private int maxSelectNum = 9;// 图片最大可选数量
    private boolean enablePreview = true;
    private int cropW = 0;
    private int cropH = 0;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private boolean isCheckNumMode = false;
    private List<PhotoInfo> pictureList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_personal_details);
        //持有者对象
        personalPresenter = new PersonalDetailsPresenter(this);
        initView();
        initData();
        initAdapter();
        initListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {

        rlPerDetail = (RelativeLayout) findViewById(R.id.rl_per_detal);
        rlPerDetail.setAlpha(0.0f);
        tvTitle = (TextView) findViewById(R.id.tv_per_detail_title);

        llCopyFavotite = (LinearLayout) findViewById(R.id.ll_copy_favorite);
        llBoardDetail = (LinearLayout) findViewById(R.id.ll_message_board_detail);
        llCopyDocument = (LinearLayout) findViewById(R.id.ll_copy_document);
        rlMineContent = (RelativeLayout) findViewById(R.id.rl_copy_mine_content);
        flBg = (FrameLayout) findViewById(R.id.fl_detail_bg);
        ivBack = (ImageView) findViewById(R.id.iv_back_per_detail);
        zommScroll = (HeaderZoomLayout) findViewById(R.id.zommLayout);
        reLowHurdle = (RelativeLayout) findViewById(R.id.personal_low_hurdle);

        multiImageView = (MultiImageView) findViewById(R.id.multiImagView);

        llAttention = (LinearLayout) findViewById(R.id.layout_attention);
        tvAttenCounts = (TextView) findViewById(R.id.tv_attention_count);
        llFens = (LinearLayout) findViewById(R.id.layout_fens);
        tvfensCounts = (TextView) findViewById(R.id.tv_fens_counts);
        llMycircle = (LinearLayout) findViewById(R.id.ll_mycircle);
        tvCircleCounts = (TextView) findViewById(R.id.tv_circle_count);

        llFavorite = (LinearLayout) findViewById(R.id.ll_favorite);     //收藏
        llMessageBoard = (LinearLayout) findViewById(R.id.ll_message_board);    //留言
        llDocument = (LinearLayout) findViewById(R.id.ll_document);     //资料

        btnAttention = (Button) findViewById(R.id.btn_personal_attention);
        btnChat = (Button) findViewById(R.id.btn_personal_chat);
        ivBg = (ImageView) findViewById(R.id.ivBg);
        ivHeadPic = (ImageView) findViewById(R.id.iv_head_pic);
        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        ivSex = (ImageView) findViewById(R.id.iv_detail_sex);

    }

    private void initData() {
        //访问者的信息
        meUser = PreUtils.getUserInfo(this);

        user = (User) getIntent().getSerializableExtra("user");

        //判断是否关注
        personalPresenter.judgeAttention(meUser.getId(), user.getId());
        //关注的总数
        personalPresenter.attentionCounts(user.getId());
        //粉丝的总数
        personalPresenter.fensCounts(user.getId());
        //圈子的总数
        personalPresenter.circleCounts(user.getId());
        //该用户所有的发图片
        personalPresenter.allPictures(user.getId());

        if (user.getuSex().equals("MM")) {
            ivSex.setBackgroundResource(R.drawable.nv);
        } else if (user.getuSex().equals("GG")) {
            ivSex.setBackgroundResource(R.drawable.nan);
        }

        //如果是自己的低栏消失
        if (meUser.getId().equals(user.getId())) {
            reLowHurdle.setVisibility(View.GONE);
        }

        //加载头像
        Glide.with(this).load(user.getHeadUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(this)).into(ivHeadPic);
        //加载背景
        Glide.with(this).load(user.getHeadBgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).bitmapTransform(new BlurTransformation(this, 20), new CenterCrop(this)).into(ivBg);
        txtUserName.setText(user.getName());

    }

    private void initPictures() {

        if (pictureList != null && pictureList.size() > 0) {
            multiImageView.setVisibility(View.VISIBLE);
            multiImageView.setMaxPics(4);
            multiImageView.setList(pictureList);
            multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //imagesize是作为loading时的图片size
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                    List<String> photoUrls = new ArrayList<String>();
                    for (PhotoInfo photoInfo : pictureList) {
                        photoUrls.add(photoInfo.url);
                    }
                    ImagePagerActivity.startImagePagerActivity((PersonalDetailsActivity.this), photoUrls, position, imageSize);
                }
            });
        } else {
            multiImageView.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {

    }

    private void initListener() {

        //点击头像
        ivHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meUser.getId().equals(user.getId())) {
                    initPicture();
                } else {
                    List<String> photoUrls = new ArrayList<>();
                    photoUrls.add(user.getHeadUrl());
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity((PersonalDetailsActivity.this), photoUrls, 0, imageSize);
                }
            }
        });

        //点击背景图片
        ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> photoUrls = new ArrayList<>();
                photoUrls.add(user.getHeadBgUrl());
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                ImagePagerActivity.startImagePagerActivity((PersonalDetailsActivity.this), photoUrls, 0, imageSize);

            }
        });

        //点击关注或取消关注
        btnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (attention) {
                    attention = false;
                    personalPresenter.cancelAttention(meUser.getId(), user.getId());
                    showTips(R.drawable.icon_per_cancle, "取消关注成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnAttention.setText("关注");
                        }
                    });
                } else {
                    attention = true;
                    personalPresenter.attentionFrient(meUser.getId(), user.getId());
                    showTips(R.drawable.icon_true, "关注成功!");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnAttention.setText("取消关注");
                        }
                    });
                }
            }
        });

        //私聊模块
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startConversation(PersonalDetailsActivity.this,
                        Conversation.ConversationType.PRIVATE, user.getId(), user.getName());
            }
        });

        //点击关注模块
        llAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalAttentionActivity.startPersonAttention(PersonalDetailsActivity.this, user.getId());
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //点击圈子
        llMycircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCircleActivity.startPersonCircleActivity(PersonalDetailsActivity.this, user);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //点击fens模块
        llFens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalFensActivity.startPersonalFensActivity(PersonalDetailsActivity.this, user.getId());
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //收藏模块
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(PersonalDetailsActivity.this, "我的收藏");
            }
        });

        //留言模块
        llMessageBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageBoardActivity.startMessageBoardActivity(PersonalDetailsActivity.this, user.getId());
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        //资料模块
        llDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalDocumentActivity.startPersonDocumentActivity(PersonalDetailsActivity.this, user);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        zommScroll.setOnScrollListener(new HeaderZoomLayout.OnScrollListener() {
            int currentY = 0;
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int mBgHeight = flBg.getMeasuredHeight();  //背景的高度
                int perDetailHeight = rlPerDetail.getMeasuredHeight();
                if (scrollY <= (mBgHeight - 64 * 4)) {
                    double pxAlpha = 1.0 / (mBgHeight + 64 * 4);
                    double aplha = scrollY * pxAlpha;
                    rlPerDetail.setAlpha((float) aplha);
                    tvTitle.setAlpha((float) aplha);
                } else {
                    rlPerDetail.setAlpha(1.0f);
                    tvTitle.setAlpha(1.0f);
                }
                //功能栏的高度
//                int mineContentHeight = rlMineContent.getMeasuredHeight();
                if (scrollY>=mBgHeight-perDetailHeight) {
                    rlMineContent.setVisibility(View.VISIBLE);
                } else {
                    rlMineContent.setVisibility(View.GONE);
                }

                //透明低栏的隐藏与显示
                if (!meUser.getId().equals(user.getId())) {
                    if ((scrollY - currentY) > 0) {
                        reLowHurdle.setVisibility(View.GONE);
                        Animation animation = AnimationUtils.loadAnimation(PersonalDetailsActivity.this, R.anim.fab_jump_to_down);
                        reLowHurdle.startAnimation(animation);
                    } else {
                        reLowHurdle.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(PersonalDetailsActivity.this, R.anim.fab_jump_from_down);
                        reLowHurdle.startAnimation(animation);
                    }
                    currentY = scrollY;


                }

            }
        });

        llCopyFavotite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(PersonalDetailsActivity.this, "收藏功能");
            }
        });
        llBoardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageBoardActivity.startMessageBoardActivity(PersonalDetailsActivity.this, user.getId());
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }
        });
        llCopyDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalDocumentActivity.startPersonDocumentActivity(PersonalDetailsActivity.this, user);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }

    private void initPicture() {
        // 进入相册
        FunctionConfig config = new FunctionConfig();
        //type --> 1图片 or 2视频
        config.setType(TYPE_IMAGE);
        //图片比例
        config.setCopyMode(copyMode);
        //是否压缩
        config.setCompress(false);
        config.setEnablePixelCompress(true);
        config.setEnableQualityCompress(true);
        config.setMaxSelectNum(maxSelectNum);
        //多选还是单选
        config.setSelectMode(FunctionConfig.MODE_SINGLE);
        //是否显示拍摄
        config.setShowCamera(true);
        //是否可以预览
        config.setEnablePreview(enablePreview);
        //是否可以剪切
        config.setEnableCrop(true);
        //是否可以预览视频
        config.setPreviewVideo(false);
        config.setRecordVideoDefinition(FunctionConfig.HIGH);// 视频清晰度
        config.setRecordVideoSecond(60);// 视频秒数
        //剪切高宽
        config.setCropW(cropW);
        config.setCropH(cropH);
        //setCheckNumMode 是否显示QQ选择风格(带数字效果)
        config.setCheckNumMode(true);
        config.setCompressQuality(100);
        config.setImageSpanCount(4);
        config.setSelectMedia(selectMedia);
        //主题 false自定义 true这是qq风格
        if (false) {
            config.setThemeStyle(ContextCompat.getColor(this, R.color.blue));
            // 可以自定义底部 预览 完成 文字的颜色和背景色
            if (!isCheckNumMode) {
                // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                config.setPreviewColor(ContextCompat.getColor(this, R.color.white));
                config.setCompleteColor(ContextCompat.getColor(this, R.color.white));
                config.setPreviewBottomBgColor(ContextCompat.getColor(this, R.color.blue));
                config.setBottomBgColor(ContextCompat.getColor(this, R.color.blue));
            }
        }

        // 先初始化参数配置，在启动相册
        PictureConfig.init(config);
        PictureConfig.getPictureConfig().openPhoto(this, resultCallback);

        // 只拍照
        //PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback);
    }

    //得到图片对象
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            if (selectMedia != null) {
                String cutPath = selectMedia.get(0).getCutPath();
                Log.e(TAG, "cutPath: " + cutPath);
                Glide.with(PersonalDetailsActivity.this)
                        .load(cutPath)
                        .asBitmap().centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(PersonalDetailsActivity.this))
                        .into(ivHeadPic);
                personalPresenter.changeHeadUrl(cutPath, user.getId());
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        super.onBackPressed();
    }

    //自定义Toast图片加文字
    private void showTips(int iconResId, String tips) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast.cancel();
            }
        } else {
            tipsToast = TipsToast.makeText(PersonalDetailsActivity.this.getApplication()
                    .getBaseContext(), tips, TipsToast.LENGTH_SHORT);
        }
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(tips);
    }

    //判断关注
    @Override
    public void update2JudgeAttention(String result) {
        if (result.equals("200")) {
            attention = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnAttention.setText("取消关注");
                }
            });

        } else if (result.equals("300")) {
            attention = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnAttention.setText("已相互关注");
                }
            });

        }
    }

    @Override
    public void updata2CancleAttention(String result) {
        if (result.equals("200")) {
            attention = false;
        }

    }

    @Override
    public void updata2attentionFrient(String result) {
        attention = true;
    }

    //获取关注总数
    @Override
    public void updata2AttentionCounts(final String result) {
        if (result != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvAttenCounts.setText(result);
                }
            });
        }
    }

    @Override
    public void updata2fensCounts(final String result) {
        if (result != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvfensCounts.setText(result);
                }
            });
        }
    }

    @Override
    public void updata2circleCounts(final String result) {
        if (result != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvCircleCounts.setText(result);
                }
            });
        }
    }

    @Override
    public void updata2AllPicture(List<PhotoInfo> pictureList) {

        this.pictureList = pictureList;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化图片
                initPictures();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PersonalDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}

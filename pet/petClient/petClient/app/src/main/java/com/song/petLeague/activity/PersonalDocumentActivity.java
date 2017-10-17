package com.song.petLeague.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.bean.User;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by song on 2017/4/14.
 */

public class PersonalDocumentActivity extends Activity {

    @Bind(R.id.tv_document_name)
    TextView tvDocumentName;
    @Bind(R.id.tv_document_sex)
    TextView tvDocumentSex;
    @Bind(R.id.tv_document_age)
    TextView tvDocumentAge;
    @Bind(R.id.tv_document_college)
    TextView tvDocumentCollege;
    @Bind(R.id.tv_document_major)
    TextView tvDocumentMajor;
    @Bind(R.id.tv_document_class)
    TextView tvDocumentClass;
    @Bind(R.id.tv_document_studentNum)
    TextView tvDocumentStudentNum;
    @Bind(R.id.tv_document_signature)
    TextView tvDocumentSignature;
    @Bind(R.id.tv_document_city)
    TextView tvDocumentCity;
    @Bind(R.id.tv_document_emotion)
    TextView tvDocumentEmotion;
    @Bind(R.id.tv_document_constellation)
    TextView tvDocumentConstellation;
    @Bind(R.id.tv_document_habbies)
    TextView tvDocumentHabbies;
    @Bind(R.id.tv_document_like_sonmething)
    TextView tvDocumentLikeSonmething;
    @Bind(R.id.tv_document_phonenum)
    TextView tvDocumentPhonenum;
    @Bind(R.id.tv_document_usually_city)
    TextView tvDocumentUsuallyCity;
    @Bind(R.id.tl_item_name)
    RelativeLayout tlItemName;
    @Bind(R.id.tl_item_sex)
    RelativeLayout tlItemSex;
    @Bind(R.id.tl_item_age)
    RelativeLayout tlItemAge;
    @Bind(R.id.tl_item_college)
    RelativeLayout tlItemCollege;
    @Bind(R.id.tl_item_major)
    RelativeLayout tlItemMajor;
    @Bind(R.id.tl_item_class)
    RelativeLayout tlItemClass;
    @Bind(R.id.tl_item_studentNumber)
    RelativeLayout tlItemStudentNumber;
    @Bind(R.id.tl_item_signature)
    RelativeLayout tlItemSignature;
    @Bind(R.id.tl_item_city)
    RelativeLayout tlItemCity;
    @Bind(R.id.tl_item_emotion)
    RelativeLayout tlItemEmotion;
    @Bind(R.id.tl_item_constellation)
    RelativeLayout tlItemConstellation;
    @Bind(R.id.tl_item_like)
    RelativeLayout tlItemLike;
    @Bind(R.id.tl_item_likeThings)
    RelativeLayout tlItemLikeThings;
    @Bind(R.id.tl_item_phoneNumber)
    RelativeLayout tlItemPhoneNumber;
    @Bind(R.id.tl_item_usuallyCity)
    RelativeLayout tlItemUsuallyCity;
    private User user;
    private ImageView ivBack;
    private TextView tvSave;
    private TextView tvTitle;
    private User userInfo;

    public static void startPersonDocumentActivity(Context context, User user) {
        Intent intent = new Intent(context, PersonalDocumentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_document);
        ButterKnife.bind(this);
        initView();
        initIntentData();
        initData();
        initListener();

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_title_bar_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_bar);

        tvSave = (TextView) findViewById(R.id.iv_title_bar_more);

    }

    private void initIntentData() {
        userInfo = PreUtils.getUserInfo(this);
        user = (User) getIntent().getSerializableExtra("user");

        if (user.getuSex().equals("GG")) {
            tvTitle.setText("他的资料");
        } else {
            tvTitle.setText("她的资料");
        }

    }

    private void initData() {
        tvDocumentName.setText(user.getName());
        tvDocumentSex.setText(user.getuSex());
        tvDocumentAge.setText(user.getuAge());
        tvDocumentCollege.setText(user.getuCollege());
        tvDocumentMajor.setText(user.getuMajor());
        tvDocumentClass.setText(user.getuClass());
        tvDocumentStudentNum.setText(user.getuStudentNumber());
        tvDocumentSignature.setText(user.getuSignature());
        tvDocumentCity.setText(user.getuCity());
        tvDocumentEmotion.setText(user.getuEmotion());
        tvDocumentConstellation.setText(user.getuConstellation());
        tvDocumentHabbies.setText(user.getuHabbies());
        tvDocumentLikeSonmething.setText(user.getuLikeSomething());
        tvDocumentPhonenum.setText(user.getuPhoneNumber());
        tvDocumentUsuallyCity.setText(user.getuUsuallyCity());

        //判断是否为本人访问
        if (userInfo.getId().equals(user.getId())) {
            tvTitle.setText("资料编辑");
            tvSave.setVisibility(View.VISIBLE);
            initListeners();
        }

    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
    }

    private void initListeners() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*PreUtils.putString("username", username, LoginActivity.this);
                PreUtils.putString("headUrl", user.getHeadUrl(), LoginActivity.this);
                PreUtils.putString("headBgUrl", user.getHeadBgUrl(), LoginActivity.this);
                PreUtils.putString("uPhoneNumbe", user.getuPhoneNumber(), LoginActivity.this);
                PreUtils.putString("uSex", user.getuSex(), LoginActivity.this);
                PreUtils.putString("uAge", user.getuAge(), LoginActivity.this);
                PreUtils.putString("uCollege", user.getuCollege(), LoginActivity.this);
                PreUtils.putString("uMajor", user.getuMajor(), LoginActivity.this);
                PreUtils.putString("uClass", user.getuClass(), LoginActivity.this);
                PreUtils.putString("uStudentNumber", user.getuStudentNumber(), LoginActivity.this);
                PreUtils.putString("uCity", user.getuCity(), LoginActivity.this);
                PreUtils.putString("uBirthday()", user.getuBirthday(), LoginActivity.this);
                PreUtils.putString("uSignature", user.getuSignature(), LoginActivity.this);
                PreUtils.putString("uConstellation", user.getuConstellation(), LoginActivity.this);
                PreUtils.putString("uEmotion", user.getuEmotion(), LoginActivity.this);
                PreUtils.putString("uUsuallyCity", user.getuUsuallyCity(), LoginActivity.this);
                PreUtils.putString("uHabbies", user.getuHabbies(), LoginActivity.this);
                PreUtils.putString("uLikeSomething", user.getuLikeSomething(), LoginActivity.this);
*/
            }
        });

        //名字
        tlItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "名字");
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }
        });
        //性别
        tlItemSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showAtCenter(PersonalDocumentActivity.this, "性别");
            }
        });
        //年龄
        tlItemAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //所在学院
        tlItemCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //专业
        tlItemMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "专业");
                startActivityForResult(intent, 200);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            });
        //班级
        tlItemClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "班级");
                startActivityForResult(intent, 300);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //学号
        tlItemStudentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //签名
        tlItemSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "签名");
                startActivityForResult(intent, 400);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //城市
        tlItemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //情感
        tlItemEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //星座
        tlItemConstellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //兴趣爱好
        tlItemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "兴趣爱好");
                startActivityForResult(intent, 500);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //喜欢做的事情
        tlItemLikeThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "喜欢做的事");
                startActivityForResult(intent, 600);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //电话号码
        tlItemPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        //常去的城市
        tlItemUsuallyCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDocumentActivity.this, PersonalDocumentEditActivity.class);
                intent.putExtra("content", "常去的城市");
                startActivityForResult(intent, 700);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = null, major = null, mclass = null, signature = null,
                like = null, likeThings = null, usuallyCity = null;
        if (data != null) {
            name = data.getStringExtra("name");
            major = data.getStringExtra("major");
            mclass = data.getStringExtra("class");
            signature = data.getStringExtra("signature");
            like = data.getStringExtra("like");
            likeThings = data.getStringExtra("likeThings");
            usuallyCity = data.getStringExtra("usuallyCity");
        }

        switch (resultCode) {
            case 100:
                tvDocumentName.setText(name);
                break;
            case 200:
                tvDocumentMajor.setText(major);
                break;
            case 300:
                tvDocumentClass.setText(mclass);
                break;
            case 400:
                tvDocumentSignature.setText(signature);
                break;
            case 500:
                tvDocumentHabbies.setText(like);
                break;
            case 600:
                tvDocumentLikeSonmething.setText(likeThings);
                break;
            case 700:
                tvDocumentUsuallyCity.setText(usuallyCity);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}

package com.song.petLeague.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.song.petLeague.MainActivity;
import com.song.petLeague.R;
import com.song.petLeague.bean.User;
import com.song.petLeague.utils.API;
import com.song.petLeague.utils.JsonUtils;
import com.song.petLeague.utils.PreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by song on 2017/3/15.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private String username;
    private String password;
    private EditText userName;
    private EditText userpwd;
    private Button enter;
    private Button register;
    private CharSequence toast;
    private RotateAnimation animRotate;
    private PopupWindow pop;
    private ImageView ivRefresh;
    Context context;
    @SuppressLint("InlinedApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initWindow();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        userName=(EditText) findViewById(R.id.username);
        userpwd=(EditText) findViewById(R.id.etUsername);
        enter=(Button) findViewById(R.id.enter);
        register=(Button) findViewById(R.id.register);
        //登录时获取用户账号信息
        getUserInfo();
        initAnim();
        enter.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  //该参数指布局能延伸到navigationbar，我们场景中不应加这个参数
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT); //设置navigationbar颜色为透明
        }
    }

    private void initPop() {
        pop = new PopupWindow(this);
        View view = View.inflate(getApplicationContext(), R.layout.login_wait_pop, null);
        pop.setAnimationStyle(R.style.popwin_anim_style3);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        backgroungAlpha(0.5f);
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        ivRefresh = (ImageView) view.findViewById(R.id.iv_login_wait);
        ivRefresh.startAnimation(animRotate);
    }
    public void backgroungAlpha(float mAlpha) {
        LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = mAlpha;
        getWindow().setAttributes(attributes);
    }
    private void initAnim() {
        animRotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animRotate.setDuration(500);
        animRotate.setRepeatCount(Animation.INFINITE);//设定无限循环
        animRotate.setFillAfter(true);// 保持状态
    }
    //保存用户的登陆记录
    @SuppressWarnings("unused")
    private void getUserInfo() {
        String username = PreUtils.getString("username", LoginActivity.this);
        String password = PreUtils.getString("password", LoginActivity.this);
        userName.setText(username);
        userpwd.setText(password);
    }
    @Override
    public void onClick(View arg0) {
//        initPop();
        switch (arg0.getId()) {
            //登录
            case R.id.enter:
                username = userName.getText().toString();
                password = userpwd.getText().toString();
                if (username.equals("") || userpwd.equals("")) {
                    toast("请输入账号或者密码！");
                    break;
                } else {
                    //用户逻辑
                    //开启一个线程，做联网操作
                    new Thread() {
                        @Override
                        public void run() {
                            postJson(username, password);
                        }
                    }.start();
                    break;
                }

          /*  case R.id.register:
                Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toReg);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
                break;*/
        }
    }

    //上传用户名跟密码到服务器
    private void postJson(final String username, final String password) {
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder().post(body).url(API.LOGIN_URL).build();
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
                if (!str.equals("100")) {
                User user = JsonUtils.parseUserJson(str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "UserJson: " + str);
                            //保存账号密码
                        }
                    });
                    //保存登录的用户账号密码
                    PreUtils.putString("id", user.getId(), LoginActivity.this);
                    PreUtils.putString("username", username, LoginActivity.this);
                    PreUtils.putString("password", password, LoginActivity.this);
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

                    //跳转页面
                    MainActivity.actionStart(LoginActivity.this);
                    finish();
                    overridePendingTransition(R.anim.in_from_right,
                            R.anim.out_to_left);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast("用户名或密码错误");
                        }
                    });

                }
            }
        });
    }

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
    private long OldTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(System.currentTimeMillis() - OldTime > 2000) {
                OldTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), "再按一次退出登录！", Toast.LENGTH_SHORT).show();
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void action2Login(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}
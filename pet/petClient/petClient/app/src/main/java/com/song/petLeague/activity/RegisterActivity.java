/*
package com.song.petLeague.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.song.petLeague.R;
import com.song.petLeague.utils.RegisterUtil;

*/
/**
 * Created by song on 2017/3/16.
 *//*

public class RegisterActivity<BmobException> extends Activity implements OnClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = "RegisterActivity";

    private Button btnNext;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etComfirmPsd;
    private EditText etPhone;

    private String username = null;
    private String password = null;
    private String comfirmPsd = null;
    private String phone = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_register);

        initView();
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

    private void initView() {
        etUsername = (EditText) findViewById(R.id.etUsername1);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etComfirmPsd = (EditText) findViewById(R.id.etComfirmPsd);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                comfirmPsd = etComfirmPsd.getText().toString();
                phone = etPhone.getText().toString();
                if(!RegisterUtil.isNetworkConnected(this)) {
                    toast("网络连接失败 ~ ~");
                } else if (username.equals("") || password.equals("")
                        || comfirmPsd.equals("") || phone.equals("")) {
                    toast("亲, 请填上完整信息");
                } else if (!comfirmPsd.equals(password)) {
                    toast("密码输入不一致，请重新输入！");
                } else if(!RegisterUtil.isPhoneNumberValid(phone)) {
                    toast("亲, 请输入正确的手机号码");
                }else {
                    Intent intent = new Intent(RegisterActivity.this, RegisterPostActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
                break;

            default:
                break;
        }
    }

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        return super.onKeyDown(keyCode, event);
    }

}
*/

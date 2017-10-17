/*
package com.song.petLeague.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.song.petLeague.R;
import com.song.petLeague.utils.API;
import com.song.petLeague.utils.ToastUtil;
import com.song.petLeague.widgets.custom.AddressPickTask;
import com.song.petLeague.widgets.custom.CustomHeaderAndFooterPicker;

import java.io.IOException;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

*/
/**
 * Created by song on 2017/4/15.
 *//*


public class RegisterPostActivity extends Activity implements View.OnClickListener{
    
    private Button etSex;
    private Button etCollege;
    private Button etCity;
    private Button etBirthday;
    private Button btnReg;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private String college;
    private String city;
    private String birthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWiondow();
        setContentView(R.layout.activity_register_post);
        initView();
        initIntentData();
        initData();
        initListener();

    }

    private void initWiondow() {
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
        etSex = (Button) findViewById(R.id.et_sex);
        etSex.setOnClickListener(this);
        etCollege = (Button) findViewById(R.id.et_college);
        etCollege.setOnClickListener(this);
        etCity = (Button) findViewById(R.id.et_city);
        etCity.setOnClickListener(this);
        etBirthday = (Button) findViewById(R.id.et_birthday);
        etBirthday.setOnClickListener(this);

        btnReg = (Button) findViewById(R.id.btn_reg);

    }

    private void initIntentData() {
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
    }

    private void initData() {

    }

    private void initListener() {
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = etSex.getText().toString();
                college = etCollege.getText().toString();
                city = etCity.getText().toString();
                birthday = etBirthday.getText().toString();
                if (sex.equals("") || college.equals("")
                        || city.equals("") || birthday.equals("")) {
                    ToastUtil.showAtCenter(RegisterPostActivity.this, "亲, 请填上完整信息");
                } else {
                    // 开始提交注册信息
                    postData2Server(username, password, phone, sex, college, city, birthday);
                }
            }
        });
    }
    private void postData2Server(String username, String password, String phone, String sex, String college, String city, String birthday) {

        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("phone", phone)
                .add("sex", sex)
                .add("college", college)
                .add("city", city)
                .add("birthday", birthday)
                .build();
        Request request = new Request.Builder().post(body).url(API.REGISTER_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("", "onFailure: " + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showAtCenter(RegisterPostActivity.this, "请检查网络!");
                    }
                });
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                if (str.equals("200")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showAtCenter(RegisterPostActivity.this,"恭喜您，注册成功!");
                        }
                    });
                    //跳转页面
                    LoginActivity.action2Login(RegisterPostActivity.this);
                    overridePendingTransition(R.anim.in_from_left,
                            R.anim.out_to_right);
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showAtCenter(RegisterPostActivity.this,"抱歉亲, 该账号已经有人注册咯~");
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_sex:
                String content[] = new String[]{
                        "MM", "GG"};
                CustomHeaderAndFooterPicker pickerSex = new CustomHeaderAndFooterPicker(this, content);
                pickerSex.setOffset(1);//显示的条目的偏移量，条数为（offset*2+1）
                pickerSex.setGravity(Gravity.CENTER);//居中
                pickerSex.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int position, String option) {
                        etSex.setText(option);
                    }
                });
                pickerSex.show();

                break;

            case R.id.et_college:
                String contentCollege[] = new String[]{
                        "数计学院", "外国语学院", "政法学院", "音舞学院",
                        "经管学院", "体育学院", "理工学院", "化生学院", "美容医学院",
                        "书法学院"};
                CustomHeaderAndFooterPicker pickerCollege = new CustomHeaderAndFooterPicker(this, contentCollege);
                pickerCollege.setOffset(1);//显示的条目的偏移量，条数为（offset*2+1）
                pickerCollege.setGravity(Gravity.CENTER);//居中
                pickerCollege.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int position, String option) {
                        etCollege.setText(option);
                    }
                });
                pickerCollege.show();
                break;

            case R.id.et_city:
                AddressPickTask task = new AddressPickTask(this);
                task.setHideCounty(true);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {

                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        etCity.setText(province.getAreaName() + " " + city.getAreaName());
                    }
                });
                task.execute("江西", "南昌");
                break;

            case R.id.et_birthday:
                final DatePicker pickerDate = new DatePicker(this);
                pickerDate.setTopPadding(2);
                pickerDate.setRangeStart(1970, 1, 11);
                pickerDate.setRangeEnd(2100, 12, 31);
                pickerDate.setSelectedItem(1994, 9, 22);
                pickerDate.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        etBirthday.setText(year + "-" + month + "-" + day);
                    }
                });
                pickerDate.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        pickerDate.setTitleText(year + "-" + pickerDate.getSelectedMonth() + "-" + pickerDate.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        pickerDate.setTitleText(pickerDate.getSelectedYear() + "-" + month + "-" + pickerDate.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        pickerDate.setTitleText(pickerDate.getSelectedYear() + "-" + pickerDate.getSelectedMonth() + "-" + day);
                    }
                });
                pickerDate.show();
                break;
        }
    }
}





















*/

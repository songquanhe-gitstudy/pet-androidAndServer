package com.song.petLeague.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.luck.picture.lib.model.FunctionConfig;
import com.song.petLeague.R;
import com.song.petLeague.adapter.MainFragmentPageAdapter;
import com.song.petLeague.fragment.EditFragement.PostImageFragment;
import com.song.petLeague.fragment.EditFragement.PostVideoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2017/3/21.
 */

public class EditCircleActivity extends FragmentActivity {

    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager editViewPager;
    private MainFragmentPageAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cirlce);
        initView();
        initData();
        initAdapter();
        initListener();

    }

    private void initView() {
        editViewPager = (ViewPager) findViewById(R.id.edit_viewpager);
    }

    private void initData() {
        PostImageFragment postImageFragment = new PostImageFragment();
        PostVideoFragment postVideoFragment = new PostVideoFragment();
        fragments.add(postImageFragment);
        fragments.add(postVideoFragment);
    }

    private void initAdapter() {
        adapter = new MainFragmentPageAdapter(getSupportFragmentManager(), fragments);
        editViewPager.setAdapter(adapter);
    }

    private void initListener() {

    }




    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 动态请求权限
     *
     * @param code
     * @param permissions
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case FunctionConfig.CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    showToast("拍照权限已被拒绝");
                }
                break;
        }
    }

    /**
     * 启动相机
     */
    protected void startCamera() {

    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

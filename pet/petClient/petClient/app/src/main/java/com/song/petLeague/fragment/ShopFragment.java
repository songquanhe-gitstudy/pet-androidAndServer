package com.song.petLeague.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.activity.ShopDetailActivity;
import com.song.petLeague.widgets.ImageCycleView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by song on 2017/3/20.
 */

public class ShopFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.ib_staple_snack)
    ImageButton ibStapleSnack;
    @Bind(R.id.ib_staple_left1)
    ImageButton ibStapleLeft1;
    @Bind(R.id.ib_staple_middle1)
    ImageButton ibStapleMiddle1;
    @Bind(R.id.ib_staple_right1)
    ImageButton ibStapleRight1;
    @Bind(R.id.ib_staple_left2)
    ImageButton ibStapleLeft2;
    @Bind(R.id.ib_staple_middle2)
    ImageButton ibStapleMiddle2;
    @Bind(R.id.ib_staple_right2)
    ImageButton ibStapleRight2;
    @Bind(R.id.ib_health_care)
    ImageButton ibHealthCare;
    @Bind(R.id.ib_health_left1)
    ImageButton ibHealthLeft1;
    @Bind(R.id.ib_health_right1)
    ImageButton ibHealthRight1;
    @Bind(R.id.ib_health_left2)
    ImageButton ibHealthLeft2;
    @Bind(R.id.ib_health_right2)
    ImageButton ibHealthRight2;
    @Bind(R.id.ib_health_left3)
    ImageButton ibHealthLeft3;
    @Bind(R.id.ib_health_right3)
    ImageButton ibHealthRight3;
    @Bind(R.id.ib_toies)
    ImageButton ibToies;
    @Bind(R.id.ib_toies_left1)
    ImageButton ibToiesLeft1;
    @Bind(R.id.ib_toies_middle1)
    ImageButton ibToiesMiddle1;
    @Bind(R.id.ib_toies_right1)
    ImageButton ibToiesRight1;
    @Bind(R.id.ib_toies_left2)
    ImageButton ibToiesLeft2;
    @Bind(R.id.ib_toies_middle2)
    ImageButton ibToiesMiddle2;
    @Bind(R.id.ib_toies_right2)
    ImageButton ibToiesRight2;
    @Bind(R.id.ib_toies_left3)
    ImageButton ibToiesLeft3;
    @Bind(R.id.ib_toies_middle3)
    ImageButton ibToiesMiddle3;
    @Bind(R.id.ib_toies_right3)
    ImageButton ibToiesRight3;
    private View view;
    private ImageCycleView imageCycleView;
    private WebView webView;
    private Button btnSearch;
    private TextView tvStaple;
    private TextView tvSnack;
    private TextView tvDaily;
    private TextView tvHealth;
    private TextView tvHealthCare;
    private TextView tvToies;

    private ImageButton ibHotRecommend;
    private ImageButton ibHotLeft;
    private ImageButton ibHotRight1;
    private ImageButton ibHotRight2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        initListener();

        return view;
    }

    private void initView() {
        imageCycleView = (ImageCycleView) view.findViewById(R.id.icv_shop_topView);
        btnSearch = (Button) view.findViewById(R.id.btn_petshop_search);
        tvStaple = (TextView) view.findViewById(R.id.tv_staple);
        tvSnack = (TextView) view.findViewById(R.id.tv_snack);
        tvDaily = (TextView) view.findViewById(R.id.tv_daily);
        tvHealth = (TextView) view.findViewById(R.id.tv_health);
        tvHealthCare = (TextView) view.findViewById(R.id.tv_health_care);
        tvToies = (TextView) view.findViewById(R.id.tv_toies);
        btnSearch.setOnClickListener(this);
        tvStaple.setOnClickListener(this);
        tvSnack.setOnClickListener(this);
        tvDaily.setOnClickListener(this);
        tvHealth.setOnClickListener(this);
        tvHealthCare.setOnClickListener(this);
        tvToies.setOnClickListener(this);

        ibHotRecommend = (ImageButton) view.findViewById(R.id.ib_hot_recommend);
        ibHotLeft = (ImageButton) view.findViewById(R.id.ib_hot_left);
        ibHotRight1 = (ImageButton) view.findViewById(R.id.ib_hot_right1);
        ibHotRight2 = (ImageButton) view.findViewById(R.id.ib_hot_right2);
        ibHotRecommend.setOnClickListener(this);
        ibHotLeft.setOnClickListener(this);
        ibHotRight1.setOnClickListener(this);
        ibHotRight2.setOnClickListener(this);

        ibStapleSnack.setOnClickListener(this);
        ibStapleLeft1.setOnClickListener(this);
        ibStapleMiddle1.setOnClickListener(this);
        ibStapleRight1.setOnClickListener(this);
        ibStapleLeft2.setOnClickListener(this);
        ibStapleMiddle2.setOnClickListener(this);
        ibStapleRight2.setOnClickListener(this);
        ibHealthCare.setOnClickListener(this);
        ibHealthLeft1.setOnClickListener(this);
        ibHealthRight1.setOnClickListener(this);
        ibHealthLeft2.setOnClickListener(this);
        ibHealthRight2.setOnClickListener(this);
        ibHealthLeft3.setOnClickListener(this);
        ibHealthRight3.setOnClickListener(this);
        ibToies.setOnClickListener(this);
        ibToiesLeft1.setOnClickListener(this);
        ibToiesMiddle1.setOnClickListener(this);
        ibToiesRight1.setOnClickListener(this);
        ibToiesLeft2.setOnClickListener(this);
        ibToiesMiddle2.setOnClickListener(this);
        ibToiesRight2.setOnClickListener(this);
        ibToiesLeft3.setOnClickListener(this);
        ibToiesMiddle3.setOnClickListener(this);
        ibToiesRight3.setOnClickListener(this);



        initBanner();
    }

    private void initBanner() {
        List<ImageCycleView.ImageInfo> list = new ArrayList<>();
        //使用网络加载数据，最后一个参数为图片新闻的id
        list.add(new ImageCycleView.ImageInfo("https://p2.ycw.com/201703/13/28e1999b40ca523e7e772a0ac76289fa_ss600", "若无其事，原来是最好的报复。生活得更好，是为了自己", "http://shop.yc.cn/activity/2017-03-14/646106.html?id=42&petTitle=%E5%88%86%E4%BA%AB&petCallback=goodsShare"));
        list.add(new ImageCycleView.ImageInfo("https://p2.ycw.com/201703/14/af4d6bc25652dcc089fda2e5e4cd9ba9_ss600", "道歉并不总是代表我承认自己错了，只能说，我在乎我们的关系，比我自身还在乎", "http://shop.yc.cn/activity/2017-03-22/129392.html?id=43&petTitle=%E5%88%86%E4%BA%AB&petCallback=goodsShare"));
        list.add(new ImageCycleView.ImageInfo("https://p2.ycw.com/201703/13/4bb6e578d776c2d9e8159449b3f116d6_ss600", "总是听从内心的声音。因为即便它长在你的左边，它却总是对的。。", "http://shop.yc.cn/scene/index.do?sceneId=223"));
        list.add(new ImageCycleView.ImageInfo("https://p2.ycw.com/201703/23/c8525371aae51836e727b54c300d8553_ss600", "一生中，女人总会爱过一两次坏蛋，才会珍惜那个对的人", "http://shop.yc.cn/scene/index.do?sceneId=251"));
        list.add(new ImageCycleView.ImageInfo("https://p2.ycw.com/201703/17/14dec14dac35dd1ea1a016e9affe61d9_ss600", "医书里说有两样东西, 是最好的灵丹妙药: 一个是开心的笑容,一个是睡个饱觉.", "http://shop.yc.cn/scene/index.do?sceneId=236"));
        imageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
                //点击跳转到文章详情界面ikh
               /* Bundle bundle = new Bundle();
                bundle.putString("typeid", "2");
                bundle.putString("id", imageInfo.value.toString());*/
                //跳转到文章详情界面
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("url", imageInfo.value.toString());
                getActivity().startActivity(intent);
            }
        });

        imageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {

                //使用BitmapUtils,只能使用网络图片
                x.view().inject(view);
                Context context = getContext();
                ImageView imageView = null;
                if (context != null) {
                    imageView = new ImageView(context);
                    x.image().bind(imageView, imageInfo.image.toString());
                }
                return imageView;
            }
        });
    }

    private void initData() {

    }

    private void initAdapter() {

    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_petshop_search:
                Intent intent1 = new Intent(getActivity(), ShopDetailActivity.class);
                intent1.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=%E7%B2%AE");
                getActivity().startActivity(intent1);
                break;

            case R.id.tv_staple:
                Intent intent2 = new Intent(getActivity(), ShopDetailActivity.class);
                intent2.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=粮&petCallback=searchEntry");
                getActivity().startActivity(intent2);
                break;

            case R.id.tv_snack:
                Intent intent3 = new Intent(getActivity(), ShopDetailActivity.class);
                intent3.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=零食&petCallback=searchEntry");
                getActivity().startActivity(intent3);
                break;

            case R.id.tv_daily:
                Intent intent4 = new Intent(getActivity(), ShopDetailActivity.class);
                intent4.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=用品&petCallback=searchEntry");
                getActivity().startActivity(intent4);
                break;

            case R.id.tv_health:
                Intent intent5 = new Intent(getActivity(), ShopDetailActivity.class);
                intent5.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=用品&petCallback=searchEntry");
                getActivity().startActivity(intent5);
                break;

            case R.id.tv_health_care:
                Intent intent6 = new Intent(getActivity(), ShopDetailActivity.class);
                intent6.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=126&petTitle=搜索&petCallback=searchEntry");
                getActivity().startActivity(intent6);
                break;

            case R.id.tv_toies:
                Intent intent7 = new Intent(getActivity(), ShopDetailActivity.class);
                intent7.putExtra("url", "http://shop.yc.cn/item/searchResult.do?keyword=%E7%8E%A9%E5%85%B7&petCallback=searchEntry");
                getActivity().startActivity(intent7);
                break;

            case R.id.ib_hot_recommend:
                Intent intent8 = new Intent(getActivity(), ShopDetailActivity.class);
                intent8.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=250&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent8);
                break;

            case R.id.ib_hot_left:
                Intent intent9 = new Intent(getActivity(), ShopDetailActivity.class);
                intent9.putExtra("url", "http://shop.yc.cn/activity/2017-03-27/821836.html?id=25&amp;amp;amp;petTitle=%E5%88%86%E4%BA%AB&amp;amp;amp;petCallback=goodsShare");
                getActivity().startActivity(intent9);
                break;

            case R.id.ib_hot_right1:
                Intent intent10 = new Intent(getActivity(), ShopDetailActivity.class);
                intent10.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=220&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent10);
                break;

            case R.id.ib_hot_right2:
                Intent intent11 = new Intent(getActivity(), ShopDetailActivity.class);
                intent11.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=62&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent11);
                break;

            case R.id.ib_staple_snack:
                Intent intent12 = new Intent(getActivity(), ShopDetailActivity.class);
                intent12.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=249&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent12);
                break;

            case R.id.ib_staple_left1:
                Intent intent13 = new Intent(getActivity(), ShopDetailActivity.class);
                intent13.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=158&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent13);
                break;

            case R.id.ib_staple_middle1:
                Intent intent14 = new Intent(getActivity(), ShopDetailActivity.class);
                intent14.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=162&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent14);
                break;

            case R.id.ib_staple_right1:
                Intent intent15 = new Intent(getActivity(), ShopDetailActivity.class);
                intent15.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=160&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent15);
                break;

            case R.id.ib_staple_left2:
                Intent intent16 = new Intent(getActivity(), ShopDetailActivity.class);
                intent16.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=159&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent16);
                break;

            case R.id.ib_staple_middle2:
                Intent intent17 = new Intent(getActivity(), ShopDetailActivity.class);
                intent17.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=161&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent17);
                break;

            case R.id.ib_staple_right2:
                Intent intent18 = new Intent(getActivity(), ShopDetailActivity.class);
                intent18.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=168&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent18);
                break;

            case R.id.ib_health_care:
                Intent intent19 = new Intent(getActivity(), ShopDetailActivity.class);
                intent19.putExtra("url", "http://shop.yc.cn/activity/2017-03-10/357478.html?id=40&petTitle=%E5%88%86%E4%BA%AB&petCallback=goodsShare");
                getActivity().startActivity(intent19);
                break;

            case R.id.ib_health_left1:
                Intent intent20 = new Intent(getActivity(), ShopDetailActivity.class);
                intent20.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=101&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent20);
                break;

            case R.id.ib_health_right1:
                Intent intent21 = new Intent(getActivity(), ShopDetailActivity.class);
                intent21.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=72&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent21);
                break;

            case R.id.ib_health_left2:
                Intent intent22 = new Intent(getActivity(), ShopDetailActivity.class);
                intent22.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=100&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent22);
                break;

            case R.id.ib_health_right2:
                Intent intent23 = new Intent(getActivity(), ShopDetailActivity.class);
                intent23.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=89&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent23);
                break;

            case R.id.ib_health_left3:
                Intent intent24 = new Intent(getActivity(), ShopDetailActivity.class);
                intent24.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=185&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent24);
                break;

            case R.id.ib_health_right3:
                Intent intent25 = new Intent(getActivity(), ShopDetailActivity.class);
                intent25.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=186&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent25);
                break;

            case R.id.ib_toies:
                Intent intent26 = new Intent(getActivity(), ShopDetailActivity.class);
                intent26.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=149&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent26);
                break;

            case R.id.ib_toies_left1:
                Intent intent27 = new Intent(getActivity(), ShopDetailActivity.class);
                intent27.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=107&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent27);
                break;

            case R.id.ib_toies_middle1:
                Intent intent28 = new Intent(getActivity(), ShopDetailActivity.class);
                intent28.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=84&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent28);
                break;

            case R.id.ib_toies_right1:
                Intent intent29 = new Intent(getActivity(), ShopDetailActivity.class);
                intent29.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=108&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent29);
                break;

            case R.id.ib_toies_left2:
                Intent intent30 = new Intent(getActivity(), ShopDetailActivity.class);
                intent30.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=190&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent30);
                break;

            case R.id.ib_toies_middle2:
                Intent intent31 = new Intent(getActivity(), ShopDetailActivity.class);
                intent31.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=187&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent31);
                break;

            case R.id.ib_toies_right2:
                Intent intent32 = new Intent(getActivity(), ShopDetailActivity.class);
                intent32.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=188&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent32);
                break;

            case R.id.ib_toies_left3:
                Intent intent33 = new Intent(getActivity(), ShopDetailActivity.class);
                intent33.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=175&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent33);
                break;

            case R.id.ib_toies_middle3:
                Intent intent34 = new Intent(getActivity(), ShopDetailActivity.class);
                intent34.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=66&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent34);
                break;

            case R.id.ib_toies_right3:
                Intent intent35 = new Intent(getActivity(), ShopDetailActivity.class);
                intent35.putExtra("url", "http://shop.yc.cn/scene/index.do?sceneId=177&petTitle=%E6%90%9C%E7%B4%A2&petCallback=searchEntry");
                getActivity().startActivity(intent35);
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

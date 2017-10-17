package com.song.petLeague.fragment.EditFragement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.song.petLeague.R;
import com.song.petLeague.adapter.GridImageAdapter;
import com.song.petLeague.fragment.innerFragment.MyCircleFragment;
import com.song.petLeague.utils.API;
import com.song.petLeague.utils.FullyGridLayoutManager;
import com.song.petLeague.widgets.dialog.UpLoadDialog;
import com.yalantis.ucrop.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.luck.picture.lib.model.LocalMediaLoader.TYPE_IMAGE;

/**
 * Created by song on 2017/3/25.
 */

/**
 * 动态编辑器
 */
//属性设置参考
/**
 * type --> 1图片 or 2视频
 * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
 * maxSelectNum --> 可选择图片的数量
 * selectMode         --> 单选 or 多选
 * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
 * isPreview    --> 是否打开预览选项
 * isCrop       --> 是否打开剪切选项
 * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
 * ThemeStyle -->主题颜色
 * CheckedBoxDrawable -->图片勾选样式
 * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
 * cropH-->裁剪高度 值不能小于100
 * isCompress -->是否压缩图片
 * setEnablePixelCompress 是否启用像素压缩
 * setEnableQualityCompress 是否启用质量压缩
 * setRecordVideoSecond 录视频的秒数，默认不限制
 * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
 * setImageSpanCount -->每行显示个数
 * setCheckNumMode 是否显示QQ选择风格(带数字效果)
 * setPreviewColor 预览文字颜色
 * setCompleteColor 完成文字颜色
 * setPreviewBottomBgColor 预览界面底部背景色
 * setBottomBgColor 选择图片页面底部背景色
 * setCompressQuality 设置裁剪质量，默认无损裁剪
 * setSelectMedia 已选择的图片
 * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
 * 注意-->type为2时 设置isPreview or isCrop 无效
 * 注意：Options可以为空，默认标准模式
 */

public class PostImageFragment extends Fragment {

    private static final String TAG = "打印";
    private View view;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int selectMode = FunctionConfig.MODE_MULTIPLE;
    private int maxSelectNum = 9;// 图片最大可选数量
    private int copyMode = FunctionConfig.CROP_MODEL_DEFAULT;
    private boolean enablePreview = true;
    private int cropW = 0;
    private int cropH = 0;
    private boolean isCheckNumMode = false;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private UpLoadDialog upLoadDialog;

    private Context mContext;

    private ImageButton ibBack;
    private TextView tv_send;
    private EditText etContent;
    private String content;
    private  String uId;

    MyCircleFragment myCircleFragment = new MyCircleFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_image, container, false);
        mContext = getActivity();
        initView();
        initData();
        initListener();
        return view;
    }

    private void initView() {
        ibBack = (ImageButton) view.findViewById(R.id.left_back);
        tv_send = (TextView) view.findViewById(R.id.tv_right);
        etContent = (EditText) view.findViewById(R.id.et_content);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

    }

    private void initData() {
        uId = getActivity().getIntent().getStringExtra("uId");
        upLoadDialog = new UpLoadDialog(getActivity());

        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        recyclerView.setAdapter(adapter);

    }

    private void initListener() {
        //发圈子动态
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG , "onClick: " + "点击了" );
                upLoadDialog.show();
                content = etContent.getText().toString();
                sendMultipart(adapter.getList());
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia);
            }
        });
    }

    private void sendMultipart(List<LocalMedia> fileList) {

        MediaType MEDIA_TYPE_PNG = MediaType.parse("*/image");
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder mbody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(LocalMedia filelist:fileList){
            File file = new File(filelist.getCutPath());
            if(file.exists()){
                Log.e("getName: ", file.getName());
                Log.e("getPath: ",file.getPath());
                Log.e("getAbsolutePath: ", file.getAbsolutePath());
                Log.e("getParent: ", file.getParent());
                //第一个参数是服务器接收的名称，第二个是上传文件的名字，第三个是上传的文件
                mbody.addFormDataPart("image", file.getName(), RequestBody.create(MEDIA_TYPE_PNG,
                        file));
            }
        }
        mbody.addFormDataPart("key", content);
        mbody.addFormDataPart("uId", uId);
        RequestBody requestBody =mbody.build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(API.ADD_POST)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("返回失败的结果： ", " " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i("返回成功的结果： ", response.body().string());
                if(response.body().string().equals("200")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            upLoadDialog.dismiss();
//                            myCircleFragment.recyclerView.setRefreshing(true);
//                            myCircleFragment.refreshListener.onRefresh();//执行数据加载操作
                            getActivity().finish();
                        }
                    });
                }
            }
        });
    }

    /**
     * 删除图片回调接口
     */

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
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
                    config.setSelectMode(FunctionConfig.MODE_MULTIPLE);
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
                        config.setThemeStyle(ContextCompat.getColor(getActivity(), R.color.blue));
                        // 可以自定义底部 预览 完成 文字的颜色和背景色
                        if (!isCheckNumMode) {
                            // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                            config.setPreviewColor(ContextCompat.getColor(getActivity(), R.color.white));
                            config.setCompleteColor(ContextCompat.getColor(getActivity(), R.color.white));
                            config.setPreviewBottomBgColor(ContextCompat.getColor(getActivity(), R.color.blue));
                            config.setBottomBgColor(ContextCompat.getColor(getActivity(), R.color.blue));
                        }
                    }
                    //图片选择样式 ture自定义  false默认
                    /*int selector = R.drawable.select_cb;
                    if (false) {
                        config.setCheckedBoxDrawable(selector);
                    }*/

                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback);

                    // 只拍照
                    //PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback);
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }
    };


    /**
     * 图片回调方法，获取得到图片对象
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 判断 一个字段的值否为空
     *
     * @param s
     * @return
     */

    public boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }
}

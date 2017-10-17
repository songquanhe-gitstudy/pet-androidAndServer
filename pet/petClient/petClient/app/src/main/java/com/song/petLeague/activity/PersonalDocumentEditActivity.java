package com.song.petLeague.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.petLeague.R;

/**
 * Created by song on 2017/4/26.
 */

public class PersonalDocumentEditActivity extends Activity {

    private ImageView ivBack;
    private TextView tvTitle, tvIntroduce, tvSure;
    private String content;
    private EditText etContent;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_edit);
        initView();
        initIntentData();
        initData();
        initListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_title_bar_back);
        tvTitle = (TextView) findViewById(R.id.tv_title_bar);
        tvSure = (TextView) findViewById(R.id.iv_title_bar_more);
        tvSure.setVisibility(View.VISIBLE);
        tvSure.setText("确定");
        etContent = (EditText) findViewById(R.id.et_content);
        tvIntroduce = (TextView) findViewById(R.id.tv_produce_content);

    }

    private void initIntentData() {
        content = getIntent().getStringExtra("content");
        if (content.equals("名字")) {
            tvTitle.setText("修改名字");
            tvIntroduce.setText("更改好的名字可以让小伙伴更好的记住你哦~");
        } else if (content.equals("专业")) {
            tvTitle.setText("填写专业");
            tvIntroduce.setText("填写完整专业吧,让同专业的小伙伴找到你。");
        } else if (content.equals("班级")) {
            tvTitle.setText("填写班级");
            tvIntroduce.setText("填写完整专业吧,让同专业的小伙伴找到你。");
        } else if (content.equals("签名")) {
            tvTitle.setText("填写签名");
            tvIntroduce.setText("填写属于你自己的风格!");
        }   else if (content.equals("兴趣爱好")) {
            tvTitle.setText("填写兴趣爱好");
            tvIntroduce.setText("填写自己的爱好，让同爱好的人找到你吧~");
        } else if (content.equals("喜欢做的事")) {
            tvTitle.setText("填写喜欢的事");
            tvIntroduce.setText("是时候展示自己喜欢做的事情的时候了!");
        } else if (content.equals("常去的城市")) {
            tvTitle.setText("填写常去的城市");
            tvIntroduce.setText("听说你在的城市下雨了, 多么希望问你是否有带雨伞!");
        }
    }

    private void initData() {

    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        //点击确定
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                if (content.equals("名字")) {
                    intent.putExtra("name", etContent.getText().toString());
                    setResult(100, intent);
                } else if (content.equals("专业")) {
                    intent.putExtra("major", etContent.getText().toString());
                    setResult(200, intent);
                }else if (content.equals("班级")) {
                    intent.putExtra("class", etContent.getText().toString());
                    setResult(300, intent);
                }else if (content.equals("签名")) {
                    intent.putExtra("signature", etContent.getText().toString());
                    setResult(400, intent);
                }else if (content.equals("兴趣爱好")) {
                    intent.putExtra("like", etContent.getText().toString());
                    setResult(500, intent);
                }else if (content.equals("喜欢做的事")) {
                    intent.putExtra("likeThings", etContent.getText().toString());
                    setResult(600, intent);
                }else if (content.equals("常去的城市")) {
                    intent.putExtra("usuallyCity", etContent.getText().toString());
                    setResult(700, intent);
                }
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}

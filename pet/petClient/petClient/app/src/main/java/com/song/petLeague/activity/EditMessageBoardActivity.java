package com.song.petLeague.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.contract.MessageBoardContract;
import com.song.petLeague.mvp.presenter.MessageBoardPresenter;
import com.song.petLeague.utils.PreUtils;

import java.util.List;

/**
 * Created by song on 2017/4/11.
 */

public class EditMessageBoardActivity extends Activity implements MessageBoardContract.View{

    private MessageBoardPresenter presenter;
    private String boardType;      //“1” 表示公开留言 “2”表示匿名留言
    private User userInfo;
    private String umId;
    private RelativeLayout rlBoard;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvSend;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message_board);
        presenter = new MessageBoardPresenter(this);
        initView();
        initIntentData();
        initData();
        initListener();

    }

    private void initView() {

        rlBoard = (RelativeLayout) findViewById(R.id.rl_board_deit);
        ivBack = (ImageView) findViewById(R.id.iv_back_edit_board);
        tvTitle = (TextView) findViewById(R.id.tv_edit_board);
        tvSend = (TextView) findViewById(R.id.iv_send__board);
        etContent = (EditText) findViewById(R.id.et_edit_content);
    }

    private void initIntentData() {
        boardType = getIntent().getStringExtra("BOARD_TYPE");
        umId = getIntent().getStringExtra("umId");
        userInfo = PreUtils.getUserInfo(this);
    }

    private void initData() {
        if (boardType.equals("1")) {
            tvTitle.setText("公开留言");
        } else if (boardType.equals("2")){
            tvTitle.setText("匿名留言");
            rlBoard.setBackgroundColor(getResources().getColor(R.color.bar_grey));
        }

    }

    private void initListener() {
        //点击返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addMessageBoard(userInfo.getId(), umId, etContent.getText().toString(), boardType);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void updata2LoadData(int refreshType, List<MessageBoardItem> boardItemList) {

    }

    @Override
    public void updata2DeleteMessageBoard(int boadPosition) {

    }

    @Override
    public void updata2showEditTextBodyVisiable(int visiavle, String uId, String msId, int boardPosition) {

    }

    @Override
    public void updata2AddBoardComment(int boardPosition, MBCommentItem mbCommentItem) {

    }

    @Override
    public void updata2AddMessageBoard() {
        finish();
    }
}

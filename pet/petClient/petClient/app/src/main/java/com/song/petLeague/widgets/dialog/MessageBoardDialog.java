package com.song.petLeague.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.bean.MessageBoardItem;
import com.song.petLeague.mvp.presenter.MessageBoardPresenter;

/**
 * Created by song on 2017/4/11.
 */

public class MessageBoardDialog extends Dialog implements View.OnClickListener{
   private Context context;
    private MessageBoardPresenter presenter;
    private MessageBoardItem boardItem;
    private int boardPosition;
    private String uId;

    public MessageBoardDialog(Context context, MessageBoardPresenter presenter, MessageBoardItem boardItem,
                              int boardPosition, String uId) {
        super(context, R.style.comment_dialog);
        this.context = context;
        this.presenter = presenter;
        this.boardItem = boardItem;
        this.boardPosition = boardPosition;
        this.uId = uId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message_board);
        initWindowParams();
        initView();
    }

    private void initWindowParams() {
        Window dialogWindow = getWindow();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams wl = dialogWindow.getAttributes();
        wl.width = (int) (display.getWidth() * 0.65);

        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(wl);

    }

    private void initView() {
        TextView tvReplt = (TextView) findViewById(R.id.tv_board_reply);
        tvReplt.setOnClickListener(this);
        TextView tvDelete = (TextView) findViewById(R.id.tv_board_delete);
        if (boardItem.getmUser().getId().equals(uId) || boardItem.getuUser().getId().equals(uId)) {
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.GONE);
        }
        tvDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_board_reply:
                if (boardItem != null) {
                    presenter.showEditTextBody(uId, boardItem.getId(), boardPosition);
                }
                dismiss();
                break;
            case R.id.tv_board_delete:
                if (presenter != null && boardItem != null) {
                    presenter.deleteMessageBoard(boardPosition, boardItem.getId());
                }
                dismiss();
                break;

        }
    }
}


















package com.song.petLeague.widgets.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by song on 2017/3/28.
 */

public class TransparentImageView extends ImageButton {
    private boolean pressed;


    public TransparentImageView(Context context) {
        super(context);
    }

    public TransparentImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransparentImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pressed) {
            canvas.drawColor(Color.parseColor("#7a000000"));
        }
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        this.pressed = pressed;
        invalidate();
    }

}

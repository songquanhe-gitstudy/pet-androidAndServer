package com.song.petLeague.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.song.petLeague.MyApplication;
import com.song.petLeague.R;
import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.spannable.CircleMovementMethod;
import com.song.petLeague.spannable.SpannableClickable;
import com.song.petLeague.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by song on 16/7/9.
 */
public class boardCommentListView extends LinearLayout {
    private int itemColor;
    private int itemSelectorColor;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<MBCommentItem> mDatas;
    private LayoutInflater layoutInflater ;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setDatas(List<MBCommentItem> datas){
        if(datas == null ){
            datas = new ArrayList<MBCommentItem>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<MBCommentItem> getDatas(){
        return mDatas;
    }

    public boardCommentListView(Context context) {
        super(context);
    }

    public boardCommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public boardCommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        }finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged(){

        removeAllViews();
        if(mDatas == null || mDatas.size() == 0){
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<mDatas.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position){
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_comment, null, false);

        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        //点击整个布局变色
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

        final MBCommentItem bean = mDatas.get(position);
        String name = bean.getuUser().getName();
        String id = bean.getId();
       /* String toReplyName = "";
        if (bean.getToReplyUser() != null) {
            toReplyName = bean.getToReplyUser().getName();
        }*/

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getuUser().getId()));

        //在有回复的情况下
       /* if (!TextUtils.isEmpty(toReplyName)) {

            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, bean.getToReplyUser().getId()));
        }
        builder.append(": ");*/
        //转换表情字符
        String contentBodyStr = bean.getMbContent();
        builder.append(UrlUtils.formatUrlString(contentBodyStr));
        commentTv.setText(builder);

        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
        commentTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if(onItemLongClickListener!=null){
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });

        return convertView;
    }

    //点击用户名
    @NonNull
    private SpannableString setClickableSpan(final String textStr, final String id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor){
                                    @Override
                                    public void onClick(View widget) {
                                        Toast.makeText(MyApplication.getContext(), textStr + " &id = " + id, Toast.LENGTH_SHORT).show();
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }



}

package com.song.petLeague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.petLeague.R;
import com.song.petLeague.bean.News;

import org.xutils.x;

import java.util.List;


/**
 * Created by xhb on 2016/1/19.
 * Listview的自定义适配器
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<News> newsItems;
    private LayoutInflater mLayoutInflater;

    public ListViewAdapter(Context context, List<News> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listview_news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            viewHolder.commentNum = (TextView) convertView.findViewById(R.id.comment);
//            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
//            viewHolder.tv_typeid = (TextView) convertView.findViewById(R.id.tv_typeid);
//            viewHolder.tv_url = (TextView) convertView.findViewById(R.id.tv_url);
            viewHolder.ivUrl = (ImageView) convertView.findViewById(R.id.iv);
            //设置tag
            convertView.setTag(viewHolder);
        } else {
            //获取缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        News news = newsItems.get(position);
        //格式化时间
        String senddate = news.getnDate().toString();
//        String time = DateUtils.dateFromat(senddate);   //上面的时间会少八小时，把java默认时区改为东八区
        viewHolder.title.setText(news.getnTitle());//文章标题
        viewHolder.tv_author.setText(news.getnAuthor());//文章作者
        viewHolder.date.setText(senddate);//文章发布时间
//        viewHolder.tv_content.setText(news.getnContent());//文章内容
//        viewHolder.praiseNum.setText(news.getnPraiseNum());//文章点赞数
        viewHolder.commentNum.setText(news.getnPraiseNum() + "");//文章评论数
        viewHolder.ivUrl.setImageResource(R.drawable.product_default);//设置默认图片
        final ImageView iv = viewHolder.ivUrl;
        //获取到图片地址
        String litpic = news.getnPicUrl();
        //如果图片地址为空，则设置默认图片
        if (litpic == null) {
            iv.setImageResource(R.drawable.product_default);
        }
        /*//地址拼接
        String imageUrl = API.DMGEAME_URL + litpic;
        //下载图片，优先使用本地缓存图片
        x.image().bind(iv,imageUrl);*/
        x.image().bind(iv,litpic);

        return convertView;
    }

    //创建一个ViewHolder保存converview的布局
    class ViewHolder {
        ImageView ivUrl;//图片
        //标题、日期、评论数、文章id、分类id、文章地址
        TextView title, date, commentNum, praiseNum,  tv_id, tv_typeid, tv_content, tv_author;
    }
}

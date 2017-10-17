package com.song.petLeague.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.song.petLeague.MyApplication;
import com.song.petLeague.R;
import com.song.petLeague.activity.ImagePagerActivity;
import com.song.petLeague.activity.PersonalDetailsActivity;
import com.song.petLeague.adapter.BaseAdapter.BaseRecycleViewAdapter;
import com.song.petLeague.adapter.viewholder.CircleViewHolder;
import com.song.petLeague.adapter.viewholder.ImageViewHolder;
import com.song.petLeague.adapter.viewholder.URLViewHolder;
import com.song.petLeague.adapter.viewholder.VideoViewHolder;
import com.song.petLeague.bean.ActionItem;
import com.song.petLeague.bean.CircleItem;
import com.song.petLeague.bean.CommentConfig;
import com.song.petLeague.bean.CommentItem;
import com.song.petLeague.bean.FavortItem;
import com.song.petLeague.bean.PhotoInfo;
import com.song.petLeague.bean.User;
import com.song.petLeague.mvp.presenter.CirclePresenter;
import com.song.petLeague.utils.GlideCircleTransform;
import com.song.petLeague.utils.PreUtils;
import com.song.petLeague.utils.UrlUtils;
import com.song.petLeague.widgets.CircleVideoView;
import com.song.petLeague.widgets.CommentListView;
import com.song.petLeague.widgets.ExpandTextView;
import com.song.petLeague.widgets.MultiImageView;
import com.song.petLeague.widgets.PraiseListView;
import com.song.petLeague.widgets.SnsPopupWindow;
import com.song.petLeague.widgets.dialog.CommentDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16/5/17.
 */
public class FriendCicleAdapter extends BaseRecycleViewAdapter {

    public final static int TYPE_HEAD = 0;

    public static final int HEADVIEW_SIZE = 1;

    private ImageView ivHead;
    private ImageView ivCiecleBg;
    private User meUser;
    private int type;

    int curPlayIndex = -1;

    private CirclePresenter presenter;
    private Context context;

    public void setCirclePresenter(CirclePresenter presenter, int type) {
        this.presenter = presenter;
        this.type = type;
    }

    public FriendCicleAdapter(Context context) {
        this.context = context;
    }

    //返回帖子的类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        int itemType = 0;
        CircleItem item = (CircleItem) datas.get(position - 1);
        if (CircleItem.TYPE_URL.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_URL;
        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_IMAGE;
        } else if (CircleItem.TYPE_VIDEO.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_VIDEO;
        }
        return itemType;
    }

    //返回头部局和每种类型的帖子的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        meUser = PreUtils.getUserInfo(context);
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {      //类型为0
            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_other_circle, parent, false);
            ivCiecleBg = (ImageView) headView.findViewById(R.id.icon_other_circle_bg);
            if (type == 2) {
                ivCiecleBg.setBackgroundResource(R.drawable.friend_circle_bg);
            } else {
                ivCiecleBg.setVisibility(View.GONE);
            }
            viewHolder = new HeaderViewHolder(headView);    //让头部局先显示出来
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_circle_item, parent, false);

            if (viewType == CircleViewHolder.TYPE_URL) {      //类型为1
                viewHolder = new URLViewHolder(view);
            } else if (viewType == CircleViewHolder.TYPE_IMAGE) {      //类型为2
                viewHolder = new ImageViewHolder(view);
            } else if (viewType == CircleViewHolder.TYPE_VIDEO) {      //类型为3
                viewHolder = new VideoViewHolder(view);
            }
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == TYPE_HEAD) {
            //HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        } else {

            //加了头部局所以帖子位置-1
            final int circlePosition = position - HEADVIEW_SIZE;
            final CircleViewHolder holder = (CircleViewHolder) viewHolder;
            final CircleItem circleItem = (CircleItem) datas.get(circlePosition);
            final String circleId = circleItem.getId();
            String uId = circleItem.getUser().getId();
            String name = circleItem.getUser().getName();
            String sex = circleItem.getUser().getuSex();
            String college = circleItem.getUser().getuCollege();
            String headImg = circleItem.getUser().getHeadUrl();
            final String content = circleItem.getContent();
            String createTime = circleItem.getCreateTime();
            final List<FavortItem> favortDatas = circleItem.getFavorters();
            final List<CommentItem> commentsDatas = circleItem.getComments();
            boolean hasFavort = circleItem.hasFavort();
            boolean hasComment = circleItem.hasComment();

            //刷新个人信息
            meUser = PreUtils.getUserInfo(context);
            //加载头像图片
            Glide.with(context).load(headImg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);


            // 点击头像
            holder.headIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonalDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", circleItem.getUser());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });


            holder.nameTv.setText(name);
            holder.tvCollege.setText(college);
            holder.timeTv.setText(createTime);
            if (sex.equals("GG")) {
                holder.ivSex.setBackgroundResource(R.drawable.nan);
            } else if (sex.equals("MM")) {
                holder.ivSex.setBackgroundResource(R.drawable.nv);
            }

            //设置文本内容的"全文"和"收起"
            if (!TextUtils.isEmpty(content)) {
                holder.contentTv.setExpand(circleItem.isExpand());
                holder.contentTv.setExpandStatusListener(new ExpandTextView.ExpandStatusListener() {
                    @Override
                    public void statusChange(boolean isExpand) {
                        circleItem.setExpand(isExpand);
                    }
                });

                holder.contentTv.setText(UrlUtils.formatUrlString(content));
            }
            holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

            if (PreUtils.getUserInfo(context).getId().equals(circleItem.getUser().getId())) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
            }
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                    if (presenter != null) {
                        presenter.deleteCircle(circleId, circlePosition);
                    }
                }
            });
            if (hasFavort || hasComment) {
                if (hasFavort) {//处理点赞列表
                    holder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            String userName = favortDatas.get(position).getUser().getName();
                            String userId = favortDatas.get(position).getUser().getId();
                            Toast.makeText(MyApplication.getContext(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.praiseListView.setDatas(favortDatas);
                    holder.praiseListView.setVisibility(View.VISIBLE);
                } else {
                    holder.praiseListView.setVisibility(View.GONE);
                }

                if (hasComment) {//处理评论列表
                    holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int commentPosition) {
                            CommentItem commentItem = commentsDatas.get(commentPosition);

                            if (PreUtils.getUserInfo(context).getId().equals(commentItem.getUser().getId())) {//复制或者删除自己的评论

                                CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition, meUser.getId());
                                dialog.show();
                            } else {//回复别人的评论
                                if (presenter != null) {
                                    CommentConfig config = new CommentConfig();
                                    config.uId = PreUtils.getUserInfo(context).getId();
                                    config.uName = PreUtils.getUserInfo(context).getName();
                                    config.circleId = circleId;
                                    config.circlePosition = circlePosition;
                                    config.commentPosition = commentPosition;
                                    config.commentType = CommentConfig.Type.REPLY;
                                    config.replyUser = commentItem.getUser();
                                    presenter.showEditTextBody(config);
                                }
                            }
                        }
                    });
                    //长按
                    holder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int commentPosition) {
                            //长按进行复制或者删除
                            CommentItem commentItem = commentsDatas.get(commentPosition);
                            CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition, meUser.getId());
                            dialog.show();
                        }
                    });
                    //显示
                    holder.commentList.setDatas(commentsDatas);
                    holder.commentList.setVisibility(View.VISIBLE);

                } else {
                    holder.commentList.setVisibility(View.GONE);
                }
                holder.digCommentBody.setVisibility(View.VISIBLE);
            } else {
                holder.digCommentBody.setVisibility(View.GONE);
            }

            //点赞与评论中间得那条线
            holder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

            final SnsPopupWindow snsPopupWindow = holder.snsPopupWindow;
            //得到当前用户的Id,判断是否已点赞

            String curUserFavortId = circleItem.getCurUserFavortId(PreUtils.getUserInfo(context).getId(), circleItem);
            if (!TextUtils.isEmpty(curUserFavortId)) {
                snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
            } else {
                snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
            }
            snsPopupWindow.update();    //刷新点赞状态
            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(circlePosition, circleId, curUserFavortId));

            //点击弹出popupwindow
            holder.snsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //弹出popupwindow
                    snsPopupWindow.showPopupWindow(view);
                }
            });

            holder.urlTipTv.setVisibility(View.GONE);
            switch (holder.viewType) {
                case CircleViewHolder.TYPE_URL:// 处理链接动态的链接内容和和图片
                    if (holder instanceof URLViewHolder) {
                        String linkImg = circleItem.getLinkImg();
                        String linkTitle = circleItem.getLinkTitle();
                        Glide.with(context).load(linkImg).into(((URLViewHolder) holder).urlImageIv);
                        ((URLViewHolder) holder).urlContentTv.setText(linkTitle);
                        ((URLViewHolder) holder).urlBody.setVisibility(View.VISIBLE);
                        ((URLViewHolder) holder).urlTipTv.setVisibility(View.VISIBLE);
                    }

                    break;
                case CircleViewHolder.TYPE_IMAGE:// 处理图片
                    if (holder instanceof ImageViewHolder) {
                        final List<PhotoInfo> photos = circleItem.getPhotos();
                        if (photos != null && photos.size() > 0) {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                            ((ImageViewHolder) holder).multiImageView.setList(photos);
                            ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //imagesize是作为loading时的图片size
                                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                                    List<String> photoUrls = new ArrayList<String>();
                                    for (PhotoInfo photoInfo : photos) {
                                        photoUrls.add(photoInfo.url);
                                    }
                                    ImagePagerActivity.startImagePagerActivity(( context), photoUrls, position, imageSize);
                                }
                            });
                        } else {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
                        }
                    }

                    break;
                case CircleViewHolder.TYPE_VIDEO://处理视频
                    if (holder instanceof VideoViewHolder) {
                        ((VideoViewHolder) holder).videoView.setVideoUrl(circleItem.getVideoUrl());
                        ((VideoViewHolder) holder).videoView.setVideoImgUrl(circleItem.getVideoImgUrl());//视频封面图片
                        ((VideoViewHolder) holder).videoView.setPostion(position);
                        ((VideoViewHolder) holder).videoView.setOnPlayClickListener(new CircleVideoView.OnPlayClickListener() {
                            @Override
                            public void onPlayClick(int pos) {
                                curPlayIndex = pos;
                            }
                        });
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;//有head需要加1
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    //让头部显示出来
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //TODO
    //进行点赞和评论操作
    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        private String mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private String circleId;

        public PopupItemClickListener(int circlePosition, String circleId, String favorId) {
            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
            this.circleId = circleId;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    if (presenter != null) {
                        if ("赞".equals(actionitem.mTitle.toString())) { //点赞
                            presenter.addFavort(mCirclePosition, circleId, PreUtils.getUserInfo(context).getId());
                        } else {//取消点赞
                            presenter.deleteFavort(mCirclePosition, circleId, mFavorId);
                        }
                    }
                    break;
                case 1://发布评论
                    if (presenter != null) {
                        CommentConfig config = new CommentConfig();
                        config.circlePosition = mCirclePosition;
                        config.commentType = CommentConfig.Type.PUBLIC;
                        config.uId = PreUtils.getUserInfo(context).getId();
                        config.uName = PreUtils.getUserInfo(context).getName();
                        config.circleId = circleId;
                        config.replyUser = null;    //没有回复者，说明是发表评论
//                      config.commentPosition = commentPosition;
                        presenter.showEditTextBody(config);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}


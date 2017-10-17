package com.song.petLeague.bean;

import android.text.TextUtils;

import java.util.List;


public class CircleItem extends BaseBean{

	public final static String TYPE_URL = "1";
	public final static String TYPE_IMG = "2";
	public final static String TYPE_VIDEO = "3";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String content;
	private String createTime;
	private String type;//1:链接  2:图片 3:视频
	private String linkImg;
	private String linkTitle;
	private List<PhotoInfo> photos;
	private List<FavortItem> favorters;
	private List<CommentItem> comments;
	private User user;
	private String videoUrl;
	private String videoImgUrl;
	private String uName;
	private String uHeadUrl;
	private boolean isExpand;

	public CircleItem() {

	}

	public CircleItem(String id, String createTime, String content, String type, String videoUrl, String videoImgUrl, User user, List<PhotoInfo> photos,
					  List<FavortItem> favorters, List<CommentItem> comments) {
		this.id = id;
		this.createTime = createTime;
		this.content = content;
		this.type = type;
		this.videoUrl = videoUrl;
		this.videoImgUrl = videoImgUrl;
		this.user = user;
		this.photos = photos;
		this.favorters = favorters;
		this.comments = comments;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuHeadUrl() {
		return uHeadUrl;
	}

	public void setuHeadUrl(String uHeadUrl) {
		this.uHeadUrl = uHeadUrl;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<FavortItem> getFavorters() {
		return favorters;
	}
	public void setFavorters(List<FavortItem> favorters) {
		this.favorters = favorters;
	}
	public List<CommentItem> getComments() {
		return comments;
	}
	public void setComments(List<CommentItem> comments) {
		this.comments = comments;
	}
	public String getLinkImg() {
		return linkImg;
	}
	public void setLinkImg(String linkImg) {
		this.linkImg = linkImg;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public List<PhotoInfo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoInfo> photos) {
		this.photos = photos;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoImgUrl() {
		return videoImgUrl;
	}

	public void setVideoImgUrl(String videoImgUrl) {
		this.videoImgUrl = videoImgUrl;
	}

	public void setExpand(boolean isExpand){
		this.isExpand = isExpand;
	}

	public boolean isExpand(){
		return this.isExpand;
	}

	public boolean hasFavort(){
		if(favorters!=null && favorters.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean hasComment(){
		if(comments!=null && comments.size()>0){
			return true;
		}
		return false;
	}

	//判断自己的id是否在帖子的喜欢列表中
	public String getCurUserFavortId(String curUserId, CircleItem circleItem){
		String favortid = "";
		if(!TextUtils.isEmpty(curUserId) && hasFavort()){
			List<FavortItem> favorterList = circleItem.getFavorters();
			for(FavortItem item : favorterList){
				if(curUserId.equals(item.getUser().getId())){
					favortid = curUserId;
					return favortid;
				}
			}
		}
		return favortid;
	}
}

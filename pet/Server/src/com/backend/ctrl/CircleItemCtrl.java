package com.backend.ctrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.backend.domain.CircleItem;
import com.backend.domain.UserFrient;
import com.backend.domain.UserItem;
import com.backend.domain.cComment;
import com.backend.domain.cPicture;
import com.backend.domain.cPraise;
import com.backend.mapper.CircleItemMapper;
import com.backend.mapper.UserFrientMapper;
import com.backend.mapper.UserItemMapper;
import com.backend.mapper.cCommentMapper;
import com.backend.mapper.cPictureMapper;
import com.backend.mapper.cPraiseMapper;
import com.core.page.Pagination;

@Controller
@RequestMapping("/Circle")
public class CircleItemCtrl {

	@Autowired 
	CircleItemMapper CircleItemService;
	@Autowired
	UserItemMapper Userservice;
	@Autowired
	cPictureMapper PostPhotoService;
	@Autowired
	cPraiseMapper PraiseItemService;
	@Autowired
	cCommentMapper tCommentService;
	@Autowired
	UserFrientMapper FrientService;
	
	
	private OutputStream out;
	private InputStream in;
	
	//得到所有朋友id的字符串
	private String getFrientId(List<UserFrient> totalsItems) {
		String resultString = "(";
		for (int i = 0; i < totalsItems.size(); ++i) {
			int friUid = totalsItems.get(i).getFriUid();

			if (i != 0) {
				resultString += ",";
			}

			resultString += "'";
			resultString += friUid;
			resultString += "'";
		}

		resultString += ")";
		System.out.println("resultString-----结果： " + resultString);
		return resultString;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getCircleList")
	String getCircleList(String currentPage, String circleType, String uId, Pagination<CircleItem> pagination
			, Pagination<UserFrient> uPagination, UserItem uEntity, cPicture pEntity, cPraise prEntity){
		System.out.println("currentPage: "+ currentPage+ ", circleType :" + circleType + ", uId: " + uId);
		List<CircleItem> circlesList = null;
		System.out.println("currentPage" + currentPage);
		pagination.setPageNum(Integer.parseInt(currentPage));
		
		if(Integer.valueOf(circleType) == 1) {
			circlesList = CircleItemService.getSpecifyTypeCircles(currentPage, pagination);
			System.out.println(circlesList.size());
		}else if(Integer.valueOf(circleType) == 2) {
			List<UserFrient> totalsItems = FrientService.getSpecityItemTotalsByuId(Integer.valueOf(uId), uPagination);
			String resultFrientId = getFrientId(totalsItems);
			circlesList = CircleItemService.getSpecifyTypeByfriIds(currentPage, resultFrientId, pagination);
			}else if(Integer.valueOf(circleType) == 3){
				circlesList = CircleItemService.getSpecifyTypeByuId(currentPage, Integer.valueOf(uId), pagination);
			}

		
		JSONObject jsonObj = new JSONObject();
		JSONObject dataJsonObj = new JSONObject();
		Integer index = 0;
		
		for(CircleItem singleInfo : circlesList){
			JSONObject singleJsonObj = new JSONObject();
			singleJsonObj.put("id", String.valueOf(singleInfo.getcId()));
			//得到发帖子用户的对象
			UserItem user = Userservice.selectByPrimaryKey(singleInfo.getuId());
			JSONObject jsonObject = JSONObject.fromObject(user);
			singleJsonObj.put("uId", jsonObject);
			//获取帖子中的图片对象集合
			List<cPicture> picturesList = PostPhotoService.getSpecifyPictures(singleInfo.getcId());
			JSONObject singleJsonObj2 = new JSONObject();
			for (int num = 0; num < picturesList.size(); num++) {
				JSONObject singleJsonObj3 = new JSONObject();
//				singleJsonObj3.put("pId", picturesList.get(num).getpId());
//				singleJsonObj3.put("cId", picturesList.get(num).getcId());
				singleJsonObj3.put("picUrl", picturesList.get(num).getpPicUrl());
				singleJsonObj3.put("width", picturesList.get(num).getpWight());
				singleJsonObj3.put("height", picturesList.get(num).getpHight());
				
				singleJsonObj2.put(num + "", singleJsonObj3);
			}
			singleJsonObj.put("photos", singleJsonObj2);
			
			//获取帖子的点赞对象集合
			List<cPraise> praisesList = PraiseItemService.getSpecifyPraises(singleInfo.getcId());
			JSONObject praiseJsonObj1 = new JSONObject();
			for (int num = 0; num < praisesList.size(); num++) {
				JSONObject praiseJsonObj2 = new JSONObject();
				praiseJsonObj2.put("id", String.valueOf(praisesList.get(num).getcId()));
				praiseJsonObj2.put("uId", String.valueOf(praisesList.get(num).getuId()));
				UserItem pUser = Userservice.selectByPrimaryKey(praisesList.get(num).getuId());
				praiseJsonObj2.put("puName", pUser.getuName());
				praiseJsonObj2.put("puHeadUrl", pUser.getuHeadUrl());
				praiseJsonObj2.put("puHeadBgUrl", pUser.getuHeadBgUrl());
				
				praiseJsonObj1.put(num + "", praiseJsonObj2);
			}
			singleJsonObj.put("praises", praiseJsonObj1);
			
			//获取帖子的评论集合
			List<cComment> commentList = tCommentService.getSpecifytComments(singleInfo.getcId());
			JSONObject commentJsonObj1 = new JSONObject();
			for(int num = 0; num < commentList.size(); num ++) {
				JSONObject commentJsonObj2 = new JSONObject();
				commentJsonObj2.put("id", "" + String.valueOf(commentList.get(num).getcCid()));
				//评论用户对象属性
				commentJsonObj2.put("cuId", String.valueOf(commentList.get(num).getuId()));
				UserItem cUser = Userservice.selectByPrimaryKey(commentList.get(num).getuId());
				commentJsonObj2.put("cuName", cUser.getuName());
				//评论回复用户对象属性
				if(commentList.get(num).getcTorepId() == null) {
					commentJsonObj2.put("toRepId", "null0");
				}else {
				commentJsonObj2.put("toRepId", String.valueOf(commentList.get(num).getcTorepId()));
				UserItem toRepUser = Userservice.selectByPrimaryKey(commentList.get(num).getcTorepId());
				commentJsonObj2.put("toRepName", toRepUser.getuName());
				}
				commentJsonObj2.put("cContent", commentList.get(num).getcContent());
				commentJsonObj1.put(num + "", commentJsonObj2);
			}
			singleJsonObj.put("comments", commentJsonObj1);
			
			singleJsonObj.put("typeid", singleInfo.getcType());
			singleJsonObj.put("content", singleInfo.getcContent());
			singleJsonObj.put("date", singleInfo.getcDate());
			singleJsonObj.put("linkTitle", singleInfo.getcLinkTitle());
			singleJsonObj.put("linkImg", singleInfo.getcLinkImg());
			
			singleJsonObj.put("videoImgUrl", singleInfo.getcVideoImgUrl());
			singleJsonObj.put("videoUrl", singleInfo.getcVideoUrl());
			dataJsonObj.put(index +"", singleJsonObj);
			++index;
		}    
		
		jsonObj.put("data", dataJsonObj);
		
		String retString = jsonObj.toString();
		return retString;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCircle")
	String deleteCircle(String circleId, CircleItem cEntity) {
		
		CircleItemService.deleteByPrimaryKey(Integer.valueOf(circleId));
		
		return "200";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/savePhotos")
	String savePhotos(HttpServletRequest request, HttpServletResponse response, CircleItem cEntity, cPicture pEntity) throws UnsupportedEncodingException, FileUploadException {
		request.setCharacterEncoding("utf-8");
		System.out.println("进入后台---");
		final String API = com.backend.defines.API.BASE_URL + "/upload/cirlce_photos";
        //获取文件上传需要保存的路径，upload文件夹需存在。  
        String path = request.getSession().getServletContext().getRealPath("/upload/cirlce_photos");
        
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try{  
        	String content = multipartRequest.getParameter("key");
        	String uId = multipartRequest.getParameter("uId");
        	Date date = new Date();
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        	String time = dateFormat.format(date);
        	cEntity.setcContent(content);
        	cEntity.setcDate(time);
        	cEntity.setcType("2");
        	cEntity.setuId(Integer.parseInt(uId));
        	CircleItemService.insertSelective(cEntity);
        	
        	List<CircleItem> circleItemList = CircleItemService.getSpecifyByuIdAndContent(Integer.parseInt(uId) , content);
        	CircleItem circleItem = circleItemList.get(0);
        	Integer getcId = circleItem.getcId();
        	
        	List<MultipartFile> fileList = multipartRequest.getFiles("image");
            for(MultipartFile item:fileList){
                //获取表单属性名字。  
            	 String name = item.getOriginalFilename();  
                    //获取路径名  再随机加个名字
                    String filename = UUID.randomUUID().toString() + item.getOriginalFilename();  
                    //图对应的帖子id
                    pEntity.setcId(getcId);
                    pEntity.setpPicUrl(API + "/" + filename);
                    PostPhotoService.insertSelective(pEntity);
                    
                    out = new FileOutputStream(new File(path,filename));  
                    in = item.getInputStream();  
                      
                    int length = 0;  
                    byte[] buf = new byte[1024 * 10];  
                    System.out.println("获取文件总量的容量:"+ item.getSize());  
                    while((length = in.read(buf))!=-1){  
                        out.write(buf,0,length);  
                    }  
                    in.close();  
                    out.close();  
                }
            return "200";
        }catch(Exception e){  
            e.printStackTrace();  
    }  
        	
		return "100";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/saveVideo")
	String saveVideo(HttpServletRequest request, HttpServletResponse response, CircleItem cEntity, cPicture pEntity) throws UnsupportedEncodingException, FileUploadException {
		request.setCharacterEncoding("utf-8");
		System.out.println("进入后台---");
		final String API = com.backend.defines.API.BASE_URL + "/upload/cirlce_photos";
        //获取文件上传需要保存的路径，upload文件夹需存在。  
        String path = request.getSession().getServletContext().getRealPath("/upload/cirlce_photos");
        
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try{  
        	
        	List<MultipartFile> fileList = multipartRequest.getFiles("image");
            for(MultipartFile item:fileList){
                //获取表单属性名字。  
            	 String name = item.getOriginalFilename();  
                    //获取路径名  再随机加个名字
                    String filename = UUID.randomUUID().toString() + item.getOriginalFilename();  
                    //图对应的帖子id
                    
                    out = new FileOutputStream(new File(path,filename));  
                    in = item.getInputStream();  
                      
                    int length = 0;  
                    byte[] buf = new byte[1024 * 10];  
                    System.out.println("获取文件总量的容量:"+ item.getSize());  
                    while((length = in.read(buf))!=-1){  
                        out.write(buf,0,length);  
                    }  
                    in.close();  
                    out.close();  
                }
            return "200";
        }catch(Exception e){  
            e.printStackTrace();  
    }  
        	
		return "100";
	}
	
}






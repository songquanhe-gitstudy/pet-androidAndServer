package com.backend.ctrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import com.backend.domain.UserItem;
import com.backend.mapper.UserFrientMapper;
import com.backend.mapper.UserItemMapper;
import com.backend.utils.RongUtils;


@Controller
@RequestMapping("/User")
public class UserCtrl {
	
	@Autowired
	UserItemMapper Userservice;
	
	@Autowired
	UserFrientMapper FrientService;
	
	private String filename;
	private OutputStream out;
	private InputStream in;
	
	
	//登录
	@ResponseBody
	@RequestMapping(value = "/getUserList")
	String getUserList(UserItem uEntity, String username, String password){
		//如果账号密码匹配成功返回该对象数据的json
		UserItem user = Userservice.selectByNameAndPwd(username, password);
		if (user != null) {
			JSONObject jsonObject = new JSONObject();
			JSONObject dataObject = new JSONObject();
			JSONObject singleJsonObj = new JSONObject();
			singleJsonObj.put("id", String.valueOf(user.getId()));
			singleJsonObj.put("name", user.getuName());
			singleJsonObj.put("pwd", user.getuPwd());
			singleJsonObj.put("headUrl", user.getuHeadUrl());
			singleJsonObj.put("headBgUrl", user.getuHeadBgUrl());
			
			if (user.getuPhoneNumber() == null) {
				singleJsonObj.put("uPhoneNumber", "");
			} else {
				singleJsonObj.put("uPhoneNumber", user.getuPhoneNumber());
			}
			
			if (user.getuSex() == null) {
				singleJsonObj.put("uSex", "");
			} else {
				singleJsonObj.put("uSex", user.getuSex());
			}
			
			if (user.getuAge() == null) {
				singleJsonObj.put("uAge", "");
			} else {
				singleJsonObj.put("uAge", user.getuAge());
			}
			
			if (user.getuCollege() == null) {
				singleJsonObj.put("uCollege", "");
			} else {
				singleJsonObj.put("uCollege", user.getuCollege());
			}
			
			if (user.getuMajor() == null) {
				singleJsonObj.put("uMajor", "");
			} else {
				singleJsonObj.put("uMajor", user.getuMajor());
			}
			
			if (user.getuClass() == null) {
				singleJsonObj.put("uClass", "");
			} else {
				singleJsonObj.put("uClass", user.getuClass());
			}
			
			if (user.getuStudentNumber() == null) {
				singleJsonObj.put("uStudentNumber", "");
			} else {
				singleJsonObj.put("uStudentNumber", user.getuStudentNumber());
			}
			
			if (user.getuCity() == null) {
				singleJsonObj.put("uCity", "");
			} else {
				singleJsonObj.put("uCity", user.getuCity());
			}
			
			if (user.getuBirthday() == null) {
				singleJsonObj.put("uBirthday", "");
			} else {
				singleJsonObj.put("uBirthday", user.getuBirthday());
			}
			
			if (user.getuSignature() == null) {
				singleJsonObj.put("uSignature", "");
			} else {
				singleJsonObj.put("uSignature", user.getuSignature());
			}
			
			if (user.getuConstellation() == null) {
				singleJsonObj.put("uConstellation", "");
			} else {
				singleJsonObj.put("uConstellation", user.getuConstellation());
			}
			
			if (user.getuEmotion() == null) {
				singleJsonObj.put("uEmotion", "");
			} else {
				singleJsonObj.put("uEmotion", user.getuEmotion());
			}
			
			if (user.getuUsuallyCity() == null) {
				singleJsonObj.put("uUsuallyCity", "");
			} else {
				singleJsonObj.put("uUsuallyCity", user.getuUsuallyCity());
			}
			
			if (user.getuHabbies() == null) {
				singleJsonObj.put("uHabbies", "");
			} else {
				singleJsonObj.put("uHabbies", user.getuHabbies());
			}
			
			if (user.getuLikeSomething() == null) {
				singleJsonObj.put("uLikeSomething", "");
			} else {
				singleJsonObj.put("uLikeSomething", user.getuLikeSomething());
			}
			
			dataObject.put("myUser", singleJsonObj);
			jsonObject.put("data", dataObject);
			String retJson = jsonObject.toString();
			return retJson;
		}
		return  "100";
	}
	
	//注册
	@ResponseBody
	@RequestMapping(value = "/getRegister")
	String getRegister(UserItem uEntity, String username, String password, String phone, String sex,
			String college, String city, String birthday){
		//检查账号是否已经被注册
		UserItem user = Userservice.selectByUserName(username);
		
		if (user != null) {
			return "100";
		}
		
		uEntity.setuHeadBgUrl(com.backend.defines.API.BASE_URL + "/upload/head_bg.jpg");
		uEntity.setuHeadUrl(com.backend.defines.API.BASE_URL + "/upload/me.png");
		uEntity.setuName(username);
		uEntity.setuPwd(password);
		uEntity.setuPhoneNumber(phone);
		uEntity.setuSex(sex);
		uEntity.setuCollege(college);
		uEntity.setuCity(city);
		uEntity.setuBirthday(birthday);
		Userservice.insertSelective(uEntity);
		
		return  "200";
	}
	
	//背景图片修改
		@ResponseBody
		@RequestMapping(value = "/savePictureBg")
		String savePictureBg(HttpServletRequest request, HttpServletResponse response, UserItem user) throws UnsupportedEncodingException, FileUploadException {
			request.setCharacterEncoding("utf-8");
			System.out.println("进入后台bg---");
			final String API = com.backend.defines.API.BASE_URL + "/upload/userBg_photos";
	        //获取文件上传需要保存的路径，upload文件夹需存在。  
	        String path = request.getSession().getServletContext().getRealPath("/upload/userBg_photos");
	        
	        
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        try{  
	        	String uId = multipartRequest.getParameter("uId");
	        	List<MultipartFile> fileList = multipartRequest.getFiles("picBg");
	            for(MultipartFile item:fileList){
	                    //获取路径名  再随机加个名字
	                    filename = UUID.randomUUID().toString() + item.getOriginalFilename();  
	                    
	                    user.setId(Integer.valueOf(uId));
	                    user.setuHeadBgUrl(API + "/" + filename);
	                    Userservice.updateByPrimaryKeySelective(user);
	                    
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
	            return API + "/" + filename;
	        }catch(Exception e){  
	            e.printStackTrace();  
	    }  
	        	
			return "100";
		}
	
		//头像图片修改
		@ResponseBody
		@RequestMapping(value = "/savePicture")
		String savePicture(HttpServletRequest request, HttpServletResponse response, UserItem user) throws UnsupportedEncodingException, FileUploadException {
			request.setCharacterEncoding("utf-8");
			System.out.println("进入后台picture---");
			final String API = com.backend.defines.API.BASE_URL + "/upload/user_photos";
			//获取文件上传需要保存的路径，upload文件夹需存在。  
			String path = request.getSession().getServletContext().getRealPath("/upload/user_photos");
			        
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			try{  
			String uId = multipartRequest.getParameter("uId");
			System.out.println("---------uId---" + uId );
			List<MultipartFile> fileList = multipartRequest.getFiles("picture");
			for(MultipartFile item:fileList){
			     filename = UUID.randomUUID().toString() + item.getOriginalFilename();  
			                    
			     user.setId(Integer.valueOf(uId));
			     user.setuHeadUrl(API + "/" + filename);
			     Userservice.updateByPrimaryKeySelective(user);
			                    
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
			     return API + "/" + filename;
			        }catch(Exception e){  
			            e.printStackTrace();  
			    }  
			        	
					return "100";
				}
		
		//获取用户token
		@ResponseBody
		@RequestMapping(value = "/getToken")
		String getToken(UserItem uEntity, String id){
			
			String token = RongUtils.getToken(id);
			
			return token;
		}
		
}











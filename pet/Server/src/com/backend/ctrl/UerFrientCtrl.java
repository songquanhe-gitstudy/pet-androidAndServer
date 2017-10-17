package com.backend.ctrl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.domain.CircleItem;
import com.backend.domain.UserFrient;
import com.backend.domain.UserItem;
import com.backend.domain.cPicture;
import com.backend.mapper.CircleItemMapper;
import com.backend.mapper.UserFrientMapper;
import com.backend.mapper.UserItemMapper;
import com.backend.mapper.cPictureMapper;
import com.core.page.Pagination;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/UserFrient")

public class UerFrientCtrl {
	
	@Autowired
	UserFrientMapper FrientService;
	@Autowired
	UserItemMapper UserItemService;
	@Autowired
	CircleItemMapper CircleItemService;
	@Autowired
	cPictureMapper cPictureService;
	
	
	private String getCircleId(List<CircleItem> cirlceItemLists) {
		String resultId = "(";
		for(int i = 0; i < cirlceItemLists.size(); i++ ) {
			int cId = cirlceItemLists.get(i).getcId();
			
			if(i != 0) {
				resultId += ",";
			}
			
			resultId += "'";
			resultId += cId;
			resultId += "'";
		}
		resultId += ")";
		System.out.println("resultIddd:   " + resultId);
		return resultId;
	}
	
	
			//判断是否关注朋友
			@ResponseBody
			@RequestMapping(value ="/judgeAttention")
			String judgeAttention(String uId, String frientId, UserItem uEntity, UserFrient fEntity) {
				System.out.println("uId: " + uId + ",   frientId"+ frientId);
						
					UserFrient frient = FrientService.getSpecityItemBy2Uid(Integer.valueOf(uId), Integer.valueOf(frientId));
					UserFrient frientToo = FrientService.getSpecityItemBy2Uid(Integer.valueOf(frientId), Integer.valueOf(uId));
					//相互关注
					if(frient != null && frientToo != null) {
						return "300";
					}else if(frient != null) {
						return "200";
					}
					
				return "100";
			}
			
			//关注朋友
			@ResponseBody
			@RequestMapping(value ="/attentionFrients")
			String attentionFrients(String uId, String frientId, UserItem uEntity, UserFrient fEntity) {
				
				UserFrient frientToo = FrientService.getSpecityItemBy2Uid(Integer.valueOf(frientId), Integer.valueOf(uId));
				
				fEntity.setuId(Integer.valueOf(uId));
				fEntity.setFriUid(Integer.valueOf(frientId));
				FrientService.insertSelective(fEntity);
				
				if(frientToo != null) {
					return "300";
				}
				return "200";
				
			}
			
			//取消关注朋友
			@ResponseBody
			@RequestMapping(value ="/cancelAttention")
			String cancelAttention(String uId, String frientId, UserItem uEntity, UserFrient fEntity) {
				
				if(uId != null && frientId != null) {
					FrientService.deleteBy2Uid(Integer.valueOf(uId), Integer.valueOf(frientId));
					return "200";
				}
				
				return "100";
			}
	
			//关注朋友的总个数
			@ResponseBody
			@RequestMapping(value ="/attentionCounts")
			String attentionCounts(String uId, UserItem uEntity, UserFrient fEntity) {
				if(uId != null){
					int totalsByuId = FrientService.getSpecityTotalsByuId(Integer.valueOf(uId));
					return String.valueOf(totalsByuId);
				}
				
				return "100";
			}
			
			
			//粉丝的总个数
			@ResponseBody
			@RequestMapping(value ="/fensCounts")
			String fensCounts(String frientId, UserItem uEntity, UserFrient fEntity) {
				if(frientId != null){
					int totalsByfrients = FrientService.getSpecityTotalsByfrientId(Integer.valueOf(frientId));
					return String.valueOf(totalsByfrients);
				}
				return "100";
			}
			
			//圈子个数
			@ResponseBody
			@RequestMapping(value ="/circleCounts")
			String circleCounts(String frientId, UserItem uEntity, UserFrient fEntity) {
				if(frientId != null){

					int totalsByfrients = CircleItemService.getSpecityTotalsByuId(Integer.valueOf(frientId));
					return String.valueOf(totalsByfrients);
				}
				return "100";
			}
			
	//关注对象数据获取
	@ResponseBody
	@RequestMapping(value = "/loadAttentionData")
	String loadAttentionData(String currentPage, String uId, UserFrient fEntity, UserItem uEntity, Pagination<UserFrient> pagination) {
		
		pagination.setPageNum(Integer.valueOf(currentPage));
		List<UserFrient> totalsItems = FrientService.getSpecityItemTotalsByuId(Integer.valueOf(uId), pagination);
		
		JSONObject jsonObj = new JSONObject();
		JSONObject dataJsonObj = new JSONObject();
		int num = 0;
		for (UserFrient userFrient : totalsItems) {
			JSONObject jsonObject = new JSONObject();
			
			UserItem user = UserItemService.selectByPrimaryKey(userFrient.getFriUid());
			JSONObject userObject = JSONObject.fromObject(user);
			jsonObject.put("user", userObject);
			dataJsonObj.put(num + "", jsonObject);
			num ++;
		}
		jsonObj.put("data", dataJsonObj);
		String resultData = jsonObj.toString();
		
		return resultData;
	}
	
	
	//关注对象数据获取
	@ResponseBody
	@RequestMapping(value = "/loadFensData")
	String loadFensData(String currentPage, String uId, UserFrient fEntity, UserItem uEntity, Pagination<UserFrient> pagination) {
		
		pagination.setPageNum(Integer.valueOf(currentPage));
		List<UserFrient> totalsItems = FrientService.getSpecityItemTotalsByfrientId(Integer.valueOf(uId), pagination);
		
		JSONObject jsonObj = new JSONObject();
		JSONObject dataJsonObj = new JSONObject();
		int num = 0;
		for (UserFrient userFrient : totalsItems) {
			JSONObject jsonObject = new JSONObject();
			
			UserItem user = UserItemService.selectByPrimaryKey(userFrient.getuId());
			JSONObject userObject = JSONObject.fromObject(user);
			jsonObject.put("user", userObject);
			dataJsonObj.put(num + "", jsonObject);
			num ++;
		}
		jsonObj.put("data", dataJsonObj);
		String resultData = jsonObj.toString();
		
		return resultData;
	}
	
	@ResponseBody
	@RequestMapping(value = ("/getAllPicture"))
	String getAllPicture(String uId) {
		String current = "shuju";
		
		List<CircleItem> cirlceItemLists = CircleItemService.getAllItemsByuId(Integer.valueOf(uId));
		String circleIds = getCircleId(cirlceItemLists);
		List<cPicture> allPicturesList = cPictureService.getAllPicturesByCircleId(current, circleIds);
		
		JSONObject jsonHead = new JSONObject();
		JSONObject jsonData = new JSONObject();
		int num = 0;
		
		for (cPicture cPicture : allPicturesList) {
			JSONObject jsonObject = new JSONObject();
			JSONObject pirturObject = JSONObject.fromObject(cPicture);
			jsonObject.put("picture", pirturObject);
			jsonData.put("" + num, jsonObject);
			num++;
		}
		jsonHead.put("data", jsonData);
		String resultJson = jsonHead.toString();
		
		return resultJson;
	}

	
}

















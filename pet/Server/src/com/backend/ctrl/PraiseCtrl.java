package com.backend.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.domain.cPraise;
import com.backend.mapper.cPraiseMapper;

@Controller
@RequestMapping("/Priase")
public class PraiseCtrl {
	
	@Autowired
	cPraiseMapper PraiseService;
	
	//删除点赞
	@ResponseBody
	@RequestMapping(value = "/deletePraise")
	String deletePraise(cPraise pEntity, String circleId, String favortUserId) {
		int parseInt = Integer.parseInt(circleId);
		int parseInt2 = Integer.parseInt(favortUserId);
		System.out.println("1: " + parseInt +",  2: " + parseInt2);
		PraiseService.deleteByCirAndUid(parseInt, parseInt2);
		return "200";
	}
	
	//增加点赞
	@ResponseBody
	@RequestMapping(value = "/addPraise")
	String AddPraise(cPraise pEntity, String circleId, String favortUserId) {
		int parseInt = Integer.parseInt(circleId);
		int parseInt2 = Integer.parseInt(favortUserId);
		System.out.println("3: " + parseInt +",  4: " + parseInt2);
		
		pEntity.setcId(Integer.parseInt(circleId));
		pEntity.setuId(Integer.parseInt(favortUserId));
		PraiseService.insertSelective(pEntity);
		
		//找到增加的对象并返回其id
		cPraise praiseItem = PraiseService.selectByCirAndUid(parseInt, parseInt2);
		String id = String.valueOf(praiseItem.getPraId());
		return id;
	}

}














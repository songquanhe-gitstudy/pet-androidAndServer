package com.backend.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.domain.cComment;
import com.backend.mapper.cCommentMapper;


@Controller
@RequestMapping("/Comment")
public class CommentsCtrl {
	@Autowired
	cCommentMapper CommentService;
	
	//增加回复评论
	@ResponseBody
	@RequestMapping(value = "/addRepComment")
	String addRepComment(cComment cEntity, String content, String circleId,
			String uId, String repId) {
		cEntity.setcId(Integer.parseInt(circleId));
		cEntity.setuId(Integer.parseInt(uId));
		cEntity.setcContent(content);
		cEntity.setcTorepId(Integer.parseInt(repId));
		CommentService.insertSelective(cEntity);
		
		return "200";
	}
	
	//增加发表评论
	@ResponseBody
	@RequestMapping(value = "/addComment")
	String addComment(cComment cEntity, String content, String circleId,
			String uId) {
		cEntity.setcId(Integer.parseInt(circleId));
		cEntity.setuId(Integer.parseInt(uId));
		cEntity.setcContent(content);
		CommentService.insertSelective(cEntity);
		
		return "200";
	}
	
	//删除评论
	@ResponseBody
	@RequestMapping(value = "/deleteComment")
	String deleteComment(cComment cEntity, String circleId) {
		
		CommentService.deleteByPrimaryKey(Integer.valueOf(circleId));
		
		return "200";
	}
	
}























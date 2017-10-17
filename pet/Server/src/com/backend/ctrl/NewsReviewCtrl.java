/*package com.backend.ctrl;

import java.sql.Time;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.domain.NewsReview;
import com.backend.mapper.NewsReviewMapper;

@Controller
@RequestMapping("/newsReview")
public class NewsReviewCtrl {
	
	@Autowired
	NewsReviewMapper NewsReviewservice;
	
	@ResponseBody
	@RequestMapping(value = "/accReview")
	String accReview(Integer type, HttpServletRequest request, NewsReview reEntity) {
		String newsId = request.getParameter("aid");
		String reviewContent = request.getParameter("msg");
		if(reviewContent != null) {
			reEntity.setNews(Short.parseShort(newsId));
			reEntity.setrDate(new Date());
			reEntity.setrTime(new Date());
			reEntity.setrDetailNum(reviewContent);//评论详情内容
			NewsReviewservice.insertSelective(reEntity);
			return "1";
		}else {
			return "0";
		}
	}

}











*/
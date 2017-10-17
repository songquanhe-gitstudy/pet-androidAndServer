package com.backend.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import com.core.page.Pagination;
import com.core.util.DateUtil;
import net.sf.json.JSONObject;

import com.backend.mapper.NewsMapper;
import com.backend.domain.News;

@Controller
@RequestMapping("/News")
public class NewsCtrl {

	@Autowired 
	NewsMapper NewsService;
	
	private Integer detailType = null;
	
	@RequestMapping(value = "/newsEdit")
	public ModelAndView newsEdit(News searchEntity, Integer type, Pagination<News> pagination){
		ModelAndView mv = new ModelAndView("/backend/newsTable");
		detailType = type;
		List<News> items = new ArrayList<News>();
		items = NewsService.getSpecifyTypeNews(type, pagination);
		mv.addObject("items", items);
		mv.addObject("type", type);
		return mv;
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(News searchEntity){
//		searchEntity.setTypeid(detailType);
		NewsService.insertSelective(searchEntity);
		String baseUrl = "redirect:/News/newsEdit.html?type=" + detailType;
		return new ModelAndView(baseUrl);
	}
	
	@RequestMapping(value = "/delete")
	public ModelAndView delete(News searchEntity){
//		NewsService.deleteByPrimaryKey(searchEntity.getMsgid());
		String baseUrl = "redirect:/News/newsEdit.html?type=" + detailType;
		return new ModelAndView(baseUrl);
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(News searchEntity){
		NewsService.updateByPrimaryKeySelective(searchEntity);
		String baseUrl = "redirect:/News/newsEdit.html?type=" + detailType;
		return new ModelAndView(baseUrl);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getNewsList")
	String getNewsList(Integer type, Integer page, Pagination<News> pagination){
		pagination.setPageNum(page);
		pagination.setPageSize(5);
		List<News> newsList = NewsService.getSpecifyTypeNews(type, pagination);
		
		JSONObject jsonObj = new JSONObject();
		JSONObject dataJsonObj = new JSONObject();
		Integer index = 0;
		for(News singleInfo : newsList){
			JSONObject singleJsonObj = new JSONObject();
			singleJsonObj.put("id", singleInfo.getnId());
			singleJsonObj.put("typeid", singleInfo.getType());
			singleJsonObj.put("title", singleInfo.getnTitle());
			String dateDate = DateUtil.getDateText(singleInfo.getnDate(), "yyyy-MM-dd HH:mm:ss");
			singleJsonObj.put("senddate", dateDate);
			singleJsonObj.put("author", singleInfo.getnAuthor());
			singleJsonObj.put("picUrl", singleInfo.getnPicUrl());
			singleJsonObj.put("content", singleInfo.getnContent());
			singleJsonObj.put("commentNum", singleInfo.getnCommentNum());
			singleJsonObj.put("praiseNum", singleInfo.getnPraiseNum());
			dataJsonObj.put(index +"", singleJsonObj);
			++index;
		}
		
		jsonObj.put("data", dataJsonObj);
		
		String retString = jsonObj.toString();
		return retString;
	}
	
	
}






package com.backend.ctrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backend.domain.MBoardComment;
import com.backend.domain.MessageBoard;
import com.backend.domain.UserItem;
import com.backend.mapper.MBoardCommentMapper;
import com.backend.mapper.MessageBoardMapper;
import com.backend.mapper.UserItemMapper;
import com.core.page.Pagination;

@Controller
@RequestMapping("/MessageBoard")

public class MessageBoardCtrl {
	
	@Autowired
	MessageBoardMapper messageBoardService;
	@Autowired
	MBoardCommentMapper mBoardCommentService;
	@Autowired
	UserItemMapper UserItemService;
	
	@ResponseBody
	@RequestMapping(value = "/getMessageList")
	String getMessageList(String currentPage, String uId, String mId, MessageBoard mEntity,
			MBoardComment mbEntity, Pagination<MessageBoard> pagination) {
		List<MessageBoard> messageLists = new ArrayList<MessageBoard>();
		pagination.setPageNum(Integer.parseInt(currentPage));
		List<MessageBoard> totalItems = messageBoardService.getTotalBoardItemsBymId(Integer.valueOf(mId), pagination);
		
		for (int i = 0; i < totalItems.size(); ++i) {
			if (totalItems.get(i).getmType().equals("1") || (String.valueOf(totalItems.get(i).getUbId()).equals(uId) &&
					totalItems.get(i).getmType().equals("2")) || String.valueOf(totalItems.get(i).getUmId()).equals(uId)) {
				messageLists.add(totalItems.get(i));
			}
		}
		
		JSONObject headJson = new JSONObject();
		JSONObject dataJosn = new JSONObject();
		int index = 0;
		for (int num = 0; num < messageLists.size(); ++num) {
			JSONObject jsonObject = new JSONObject();
			int id = messageLists.get(num).getId();
			List<MBoardComment> totalcommentByMid = mBoardCommentService.getTotalcommentByMid(id);
			
			JSONObject commentListJson = new JSONObject();
			
			for (int commentNum = 0; commentNum < totalcommentByMid.size(); ++commentNum) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", totalcommentByMid.get(commentNum).getId());
				jsonObject2.put("mId", totalcommentByMid.get(commentNum).getmId());
				jsonObject2.put("content", totalcommentByMid.get(commentNum).getMbContent());
				
				UserItem uUserItme = UserItemService.selectByPrimaryKey(totalcommentByMid.get(commentNum).getuId());
				jsonObject2.put("uUser", uUserItme);
				
				commentListJson.put(commentNum + "", jsonObject2);
			}
			jsonObject.put("comment", commentListJson);
			
			
			UserItem ubUserItme = UserItemService.selectByPrimaryKey(messageLists.get(num).getUbId());
			UserItem umUserItme = UserItemService.selectByPrimaryKey(messageLists.get(num).getUmId());
			jsonObject.put("mId", messageLists.get(num).getId());
			jsonObject.put("ubUser", ubUserItme);
			jsonObject.put("umUser", umUserItme);
			jsonObject.put("mContent", messageLists.get(num).getmContent());
			jsonObject.put("mData", messageLists.get(num).getmData());
			jsonObject.put("type", messageLists.get(num).getmType());
			
			dataJosn.put(index + "", jsonObject);
			index ++;
		}
		headJson.put("data", dataJosn);
		String result = headJson.toString();
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteMessageBoard")
	String deleteMessageBoard(String uId) {
		
		if(uId != null) {
			messageBoardService.deleteByPrimaryKey(Integer.parseInt(uId));
			return "200";
		}
		return "100";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addBoardCommnet")
	String addBoardCommnet(String content, String uId, String msId, MBoardComment mbEntity) {
		mbEntity.setMbContent(content);
		mbEntity.setmId(Integer.parseInt(msId));
		mbEntity.setuId(Integer.parseInt(uId));
		
		mBoardCommentService.insertSelective(mbEntity);
	
		return "200";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addMessageBoard")
	String addMessageBoard(String ubId, String umId, String mContent, String boardType, MessageBoard mEntity) {
		mEntity.setUbId(Integer.parseInt(ubId));
		mEntity.setUmId(Integer.parseInt(umId));
		mEntity.setmContent(mContent);
		mEntity.setmType(boardType);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm_ss");
		Date date = new Date();
		String time = format.format(date);
		mEntity.setmData(time);
		
		messageBoardService.insertSelective(mEntity);
	
		return "200";
	}
}











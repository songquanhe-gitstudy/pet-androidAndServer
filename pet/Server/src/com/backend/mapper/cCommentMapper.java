package com.backend.mapper;

import java.util.List;

import com.backend.domain.cComment;

public interface cCommentMapper {
    int deleteByPrimaryKey(Integer cCid);

    int insert(cComment record);

    int insertSelective(cComment record);

    cComment selectByPrimaryKey(Integer cCid);

    int updateByPrimaryKeySelective(cComment record);
    
    List<cComment> getSpecifytComments(int cId);
    
    //增加评论
    int insertByIds(int cId, int uId, int repId,String content);
}
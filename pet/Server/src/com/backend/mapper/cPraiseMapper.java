package com.backend.mapper;

import java.util.List;

import com.backend.domain.cPraise;

public interface cPraiseMapper {
    int deleteByPrimaryKey(Integer praId);

    int insert(cPraise record);

    int insertSelective(cPraise record);

    cPraise selectByPrimaryKey(Integer praId);

    int updateByPrimaryKeySelective(cPraise record);

    int updateByPrimaryKey(cPraise record);
    
    List<cPraise> getSpecifyPraises(int cId);
    
    //根据帖子id和用户id删除点赞记录
    int deleteByCirAndUid(int criId, int uId);
    
    //点赞的总次数
    int getTotalCountPraises();
    
    cPraise selectByCirAndUid(int cId, int uId);
    
}
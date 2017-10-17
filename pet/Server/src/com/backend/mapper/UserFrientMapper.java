package com.backend.mapper;

import java.util.List;

import com.backend.domain.UserFrient;
import com.core.page.Pagination;

public interface UserFrientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFrient record);

    int insertSelective(UserFrient record);

    UserFrient selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFrient record);

    int updateByPrimaryKey(UserFrient record);
    
    UserFrient getSpecityItemBy2Uid(int uId, int frientId);
    
    int deleteBy2Uid(int uId, int frientId);
    
    //获得关注总数
    int getSpecityTotalsByuId(int uId);
    
    //获取粉丝总数
    int getSpecityTotalsByfrientId(int frientId);
    
    //获取关注对象集合
    List<UserFrient> getSpecityItemTotalsByuId(int uId, Pagination<UserFrient> pagination);

    //获取粉丝对象集合
    List<UserFrient> getSpecityItemTotalsByfrientId(int friientId, Pagination<UserFrient> pagination);
}














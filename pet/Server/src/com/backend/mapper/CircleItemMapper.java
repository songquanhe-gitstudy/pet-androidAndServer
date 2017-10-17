package com.backend.mapper;

import java.util.List;

import com.backend.domain.CircleItem;
import com.core.page.Pagination;

public interface CircleItemMapper {
    int deleteByPrimaryKey(Integer cId);

    int insert(CircleItem record);

    int insertSelective(CircleItem record);

    CircleItem selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(CircleItem record);

    int updateByPrimaryKey(CircleItem record);
    
    List<CircleItem> getSpecifyTypeCircles(String current, Pagination<CircleItem> pagination);
    
    //删除帖子
    int deleteByCircleId(String id);
    
    //帖子的数量
    int getTotalCountCircles();
    
    List<CircleItem> getSpecifyByuIdAndContent(int uId, String content);
    
    List<CircleItem> getSpecifyTypeByfriIds(String current, String resultFrientId, Pagination<CircleItem> pagination);
    
    List<CircleItem> getSpecifyTypeByuId(String current, int uId, Pagination<CircleItem> pagination);
    
    List<CircleItem> getAllItemsByuId(int uId);
    
    //获得圈子个数
    int getSpecityTotalsByuId(int frientId);
}





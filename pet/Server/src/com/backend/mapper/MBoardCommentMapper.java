package com.backend.mapper;

import java.util.List;

import com.backend.domain.MBoardComment;

public interface MBoardCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MBoardComment record);

    int insertSelective(MBoardComment record);

    MBoardComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MBoardComment record);

    int updateByPrimaryKey(MBoardComment record);
    
    List<MBoardComment> getTotalcommentByMid(int mid);
    
}
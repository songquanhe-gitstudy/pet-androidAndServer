package com.backend.mapper;

import java.util.List;

import com.backend.domain.MessageBoard;
import com.core.page.Pagination;

public interface MessageBoardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageBoard record);

    int insertSelective(MessageBoard record);

    MessageBoard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageBoard record);

    int updateByPrimaryKey(MessageBoard record);
    
    List<MessageBoard> getTotalBoardItemsBymId(int mid, Pagination<MessageBoard> pagination);
}
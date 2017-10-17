package com.backend.mapper;

import java.util.List;

import com.backend.domain.News;
import com.core.page.Pagination;

public interface NewsMapper {
    int deleteByPrimaryKey(Integer msgid);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer msgid);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);
    
    List<News> getSpecifyTypeNews(Integer type, Pagination<News> pagination);
}
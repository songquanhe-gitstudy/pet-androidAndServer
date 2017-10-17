package com.backend.mapper;

import java.util.List;

import com.backend.domain.cPicture;

public interface cPictureMapper {
    int deleteByPrimaryKey(Integer pId);

    int insert(cPicture record);

    int insertSelective(cPicture record);

    cPicture selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(cPicture record);

    int updateByPrimaryKey(cPicture record);
    
    List<cPicture> getSpecifyPictures(int cid);
    
    List<cPicture> getAllPicturesByCircleId(String current, String circleIds);
}
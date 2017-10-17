package com.backend.mapper;

import com.backend.domain.UserItem;

public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItem record);

    int insertSelective(UserItem record);

    UserItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserItem record);

    int updateByPrimaryKey(UserItem record);
    
    UserItem selectByNameAndPwd(String username, String password);
    
    UserItem selectByUserName(String username);

    //获得所有用户的数量
    Integer getTotalCountUser();
}
package com.backend.mapper;

import com.backend.domain.AdminAccount;

public interface AdminAccountMapper {
    int deleteByPrimaryKey(String accountName);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    AdminAccount selectByPrimaryKey(String accountName);

    int updateByPrimaryKeySelective(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);
}
package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.BackendUser;

public interface BackendUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(BackendUser record);

    int insertSelective(BackendUser record);

    BackendUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BackendUser record);

    int updateByPrimaryKey(BackendUser record);

    BackendUser selectByInselective(BackendUser backendUser);
}
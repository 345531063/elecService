package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.UserTestResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTestResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserTestResult record);

    int insertSelective(UserTestResult record);

    UserTestResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserTestResult record);

    int updateByPrimaryKey(UserTestResult record);

    int batchInsert(List<UserTestResult> userTestResultList);

    int selectUserTestResultByThemeId(UserTestResult record);
}
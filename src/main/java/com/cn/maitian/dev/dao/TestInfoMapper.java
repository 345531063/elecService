package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.TestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestInfo record);

    int insertSelective(TestInfo record);

    TestInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestInfo record);

    int updateByPrimaryKey(TestInfo record);

    List<TestInfo> queryTestInfoList(TestInfo testInfo);

    int selectMaxTestInfoByThemeId(TestInfo testInfo);

    int queryTestInfoListCount(TestInfo userLotteryRecord);

    List<TestInfo> randomTestInfoList(TestInfo testInfo);
}
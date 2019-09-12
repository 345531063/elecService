package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.ThemeActivity;
import com.cn.maitian.dev.entity.UserLotteryRecord;
import com.cn.maitian.dev.model.UserLotteryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLotteryRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLotteryRecord record);

    int insertSelective(UserLotteryRecord record);

    UserLotteryRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLotteryRecord record);

    int updateByPrimaryKey(UserLotteryRecord record);

    List<UserLotteryRecord> selectUserLotteryRecord(UserLotteryModel themeActivity);

    int selectUserLotteryRecordCount(UserLotteryModel baseModel);
}
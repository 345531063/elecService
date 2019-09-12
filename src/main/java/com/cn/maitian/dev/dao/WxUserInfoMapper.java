package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.UserLotteryRecord;
import com.cn.maitian.dev.entity.WxUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxUserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxUserInfo record);

    int insertSelective(WxUserInfo record);

    WxUserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxUserInfo record);

    int updateByPrimaryKey(WxUserInfo record);

    WxUserInfo selectWxUserInfoSelective(WxUserInfo themeActivity);

    int batchInsert(List<WxUserInfo> list);

    int deleteAll();
}
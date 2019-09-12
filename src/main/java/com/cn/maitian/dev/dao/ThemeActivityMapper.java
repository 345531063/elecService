package com.cn.maitian.dev.dao;

import com.cn.maitian.dev.entity.TestInfo;
import com.cn.maitian.dev.entity.ThemeActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(ThemeActivity record);

    int insertSelective(ThemeActivity record);

    ThemeActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ThemeActivity record);

    int updateByPrimaryKey(ThemeActivity record);

    List<ThemeActivity> selectThemeActivityByDate(ThemeActivity themeActivityParam);

    void modifyThemeActivityAll();

    int selectThemeActivityByDateCount(ThemeActivity userLotteryRecord);
}
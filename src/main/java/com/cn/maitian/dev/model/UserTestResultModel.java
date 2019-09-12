package com.cn.maitian.dev.model;

import com.cn.maitian.dev.entity.UserTestResult;

import java.util.List;

public class UserTestResultModel {
    private String wxUserId;
    private String themeId;
    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    private List<UserTestResult> userTestResultList;

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public List<UserTestResult> getUserTestResultList() {
        return userTestResultList;
    }

    public void setUserTestResultList(List<UserTestResult> userTestResultList) {
        this.userTestResultList = userTestResultList;
    }
}

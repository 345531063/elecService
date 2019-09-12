package com.cn.maitian.dev.controller;

import com.alibaba.druid.util.StringUtils;
import com.cn.maitian.dev.annotation.CheckLogin;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.TestInfo;
import com.cn.maitian.dev.entity.ThemeActivity;
import com.cn.maitian.dev.entity.UserTestResult;
import com.cn.maitian.dev.model.BaseModel;
import com.cn.maitian.dev.model.UserTestResultModel;
import com.cn.maitian.dev.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
* @Description:测试题实现类
* @Author: steven.zhang
* @Date: 2019/9/10
*/
@RestController
@RequestMapping(value="dfny")
public class TestQuestionController {
    @Autowired
    TestQuestionService testQuestionService;
    /**** 
    * @Description: 添加、修改主题
    * @Param: [themeActivity] 
    * @return: java.lang.String 
    * @Author: steven.zhang
    * @Date: 2019/9/11 
    */
    @RequestMapping(value = "/testQuestion/addOrModifyThemeActivity",method = RequestMethod.POST)
    @CheckLogin(value=true)
    public Response addOrModifyThemeActivity(@RequestBody ThemeActivity themeActivity){
        Response response = new Response();
        String name      = themeActivity.getName();
        String startTime = themeActivity.getStartTime();
        String endTime   = themeActivity.getEndTime();

        if(null == name || "".equals(name) || null == startTime || "".equals(startTime)
           || null == endTime ||"".equals(endTime)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = testQuestionService.addOrModifyThemeActivity(themeActivity);
        return response;
    }
    /*** 
    * @Description: 获取主题信息
    * @Param:  
    * @return:  
    * @Author: steven.zhang
    * @Date: 2019/9/12 
    */
    @RequestMapping(value = "/testQuestion/queryThemeActivityList",method = RequestMethod.POST)
    @CheckLogin(value=true)
    public Response queryThemeActivityList(@RequestBody BaseModel themeActivity){
        Response response = new Response();
        response = testQuestionService.queryThemeActivityList(themeActivity);
        return response;
    }

    /****
     * @Description: 添加、修改考题
     * @Param: [themeActivity]
     * @return: java.lang.String
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/testQuestion/addOrModifyThemeTest")
    @CheckLogin(value=true)
    public Response addOrModifyThemeTest(@RequestBody TestInfo testInfo){
        Response response = new Response();
        String   title           = testInfo.getTitle();
        String  answer        = testInfo.getAnswer();
        String  content       = testInfo.getContent();
        String  themeId       =  testInfo.getThemeId();

        if(null == title || "".equals(title) || null == answer || "".equals(answer)
                || null == content ||"".equals(content)||null == themeId ||"".equals(themeId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = testQuestionService.addOrModifyTestInfo(testInfo);
        return response;
    }
    /****
     * @Description: 记录考试结果
     * @Param: [themeActivity]
     * @return: java.lang.String
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/testQuestion/recordUserTestResult")
    public Response recordUserTestResult(@RequestBody UserTestResultModel userTestResultModel){
        Response response = new Response();
        String   themeId           = userTestResultModel.getThemeId();
        String    wxUserId        = userTestResultModel.getWxUserId();
        String  companyId    = userTestResultModel.getCompanyId();
        if(StringUtils.isEmpty(wxUserId) || StringUtils.isEmpty(themeId)||
                StringUtils.isEmpty(companyId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = testQuestionService.recordUserTestResult(userTestResultModel.getUserTestResultList(),themeId,wxUserId);
        return response;
    }
    /****
     * @Description: 获取主题考题信息列表 并且判断了抽奖资格和抽奖结果[随机出题]
     * @Param: [themeActivity]
     * @return: java.lang.String
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/testQuestion/queryTestList")
    public Response queryTestList(@RequestBody BaseModel baseModel){
        Response response = new Response();
        String    wxUserId        = baseModel.getWxUserId();
        //刚刚进入没有themeId
        if(null == wxUserId || "".equals(wxUserId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = testQuestionService.randomSelectTestList(wxUserId);
        return response;
    }
    /****
     * @Description: 后台试题列表
     * @Param: [themeActivity]
     * @return: java.lang.String
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/testQuestion/backendTestList")
    @CheckLogin(value=true)
    public Response backendTestList(@RequestBody BaseModel baseModel){
        Response response = new Response();
        response = testQuestionService.queryTestInfoList(baseModel);
        return response;
    }
}

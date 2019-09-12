package com.cn.maitian.dev.service;

import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.TestInfo;
import com.cn.maitian.dev.entity.ThemeActivity;
import com.cn.maitian.dev.entity.UserTestResult;
import com.cn.maitian.dev.model.BaseModel;

import java.util.List;

public interface TestQuestionService {


    /***
     * @Description: 添加主题
     * @Param: themeName 主题名称
     *        startTime: 活动开始时间
     *        endTime:活动结束时间
     *        lotteryStatus:本主题是否可抽奖
     *        answerTimes:答题次数
     *        lotteryAnswerLimit：达到抽奖答题数
     * @return: Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response addOrModifyThemeActivity(ThemeActivity themeActivity);
    /***
     * @Description: 添加试题
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response addOrModifyTestInfo(TestInfo testInfo);

    /***
     * @Description: 查询试题详情
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response queryTestInfo(TestInfo testInfo);

    /***
     * @Description: 记录用户考试结果
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response recordUserTestResult(List<UserTestResult> userTestResult,String themeId,String wxUserId);

    /***
     * @Description: 随机抽取题根据主题中设置的随机抽题数
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response randomSelectTestList(String wxUserId);
    /*** 
    * @Description: 查询主题列表
    * @Param: [themeActivity] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/12 
    */
    Response queryThemeActivityList(BaseModel baseModel);
    /***
     * @Description: 试题列表
     * @Param: [themeActivity]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/12
     */
    public Response queryTestInfoList(BaseModel baseModel);
}

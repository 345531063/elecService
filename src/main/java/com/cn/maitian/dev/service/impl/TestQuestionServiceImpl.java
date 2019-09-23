package com.cn.maitian.dev.service.impl;

import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.dao.TestInfoMapper;
import com.cn.maitian.dev.dao.ThemeActivityMapper;
import com.cn.maitian.dev.dao.UserTestResultMapper;
import com.cn.maitian.dev.dao.WxUserInfoMapper;
import com.cn.maitian.dev.entity.*;
import com.cn.maitian.dev.model.BaseModel;
import com.cn.maitian.dev.model.TestInfoModel;
import com.cn.maitian.dev.service.TestQuestionService;
import com.cn.maitian.dev.util.DateUtil;
import com.cn.maitian.dev.util.LogUtil;
import com.cn.maitian.dev.util.StrUtils;
import com.cn.maitian.dev.util.StringOrderUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.json.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {
    @Autowired
    TestInfoMapper testInfoMapper;
    @Autowired
    ThemeActivityMapper themeActivityMapper;
    @Autowired
    UserTestResultMapper userTestResultMapper;
    @Autowired
    WxUserInfoMapper wxUserInfoMapper;
    /*** 
    * @Description:  添加主题 
    * @Param: [themeActivity] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @Override
    @Transactional
    public Response addOrModifyThemeActivity(ThemeActivity themeActivity) {
        Response response = new Response();
        String  id = themeActivity.getId();
        //将所有数据装都更高未禁用


        //将当前活动主题改为启动

        if(null == id || "".equals(id)){
            themeActivity.setStatus(1);
            themeActivityMapper.modifyThemeActivityAll();
            WxUserInfo wxUserInfo = new WxUserInfo();
            wxUserInfo.setAnswerStatus(0);
            wxUserInfo.setLotteryTimes(0);
            wxUserInfo.setThemeId(null);
            wxUserInfoMapper.updateUserTestClean(wxUserInfo);
            id = StrUtils.generate("");
            themeActivity.setId(id);
            int i = themeActivityMapper.insert(themeActivity);
            if(i > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
            }
        }else{
            if(themeActivity.getStatus() == 1){//只有状态为开启时 才禁用其他
                WxUserInfo wxUserInfo = new WxUserInfo();
                wxUserInfo.setAnswerStatus(0);
                wxUserInfo.setLotteryTimes(0);
                wxUserInfo.setThemeId(null);
                wxUserInfoMapper.updateUserTestClean(wxUserInfo);
                themeActivityMapper.modifyThemeActivityAll();
            }
            themeActivityMapper.updateByPrimaryKeySelective(themeActivity);
        }
        response.setResult(ErrorCodeEnum.SUCCESS);
        return response;
    }
    /*** 
    * @Description: 添加考试试题
    * @Param: [testInfo] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @Override
    public Response addOrModifyTestInfo(TestInfo testInfo) {
        Response response = new Response();
        String  id = testInfo.getId();
        testInfo.setCreateTime(DateUtil.getDateTime(new Date()));
        if(null == id || "".equals(id)){
            id = StrUtils.generate("");
            testInfo.setId(id);
            //获取当前题号 暂略
            int num = testInfoMapper.selectMaxTestInfoByThemeId(testInfo);
            num = num +1;
            testInfo.setQuestionNum(num);
            int i = testInfoMapper.insert(testInfo);
            if(i > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
            }
        }else{
            testInfoMapper.updateByPrimaryKeySelective(testInfo);
        }
        response.setResult(ErrorCodeEnum.SUCCESS);
        return response;
    }

    @Override
    public Response queryTestInfo(TestInfo testInfo) {
        Response response = new Response();
        String  id = testInfo.getId();
        TestInfo testInfoResult = testInfoMapper.selectByPrimaryKey(id);
        response.setResultV1(testInfoResult);
        return response;
    }
    /**** 
    * @Description: 记录用户考试记录，返回用户抽奖资格
    * @Param: [userTestResult] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @Override
    public Response recordUserTestResult(List<UserTestResult> userTestResultList,String themeId,String wxUserId,String companyId) {
        Response response = new Response();
        try{
            for(UserTestResult userTestResult :userTestResultList){
                userTestResult.setThemeId(themeId);
                userTestResult.setWxUserId(wxUserId);
                userTestResult.setId(StrUtils.generate(""));
                userTestResult.setCreateTime(DateUtil.getDateTime(new Date()));
                userTestResult.setCompanyId(companyId);
            }
            //判断当前答题次数是否已经达到上限,是否过期
            ThemeActivity themeActivityParam = new ThemeActivity();
            String startTime = DateUtil.getDateTime(new Date());
            themeActivityParam.setStartTime(startTime);
            themeActivityParam.setStatus(1);

            List<ThemeActivity> themeActivityDate =   themeActivityMapper.selectThemeActivityByDate(themeActivityParam);
            if(null == themeActivityDate || themeActivityDate.size() == 0){
                response.setResult(ErrorCodeEnum.ACTIVITYISOVER);
                return response;
            }
            WxUserInfo paramUserInfo = new WxUserInfo();
//            paramUserInfo.setThemeId(themeId);
            paramUserInfo.setId(wxUserId);
            WxUserInfo userInfo = wxUserInfoMapper.selectWxUserInfoSelective(paramUserInfo);
            int  isAnswer    = 0;
            if(themeId.equals(userInfo.getThemeId()) ){//之前答过一次题 才会存在相同的themeId
                  isAnswer    = userInfo.getAnswerStatus();
            }
            int  answerTimes = themeActivityDate.get(0).getAnswerTimes();//答题次数
            if(isAnswer > 0 && answerTimes <= (isAnswer) ){//表示已经答题
                response.setResult(ErrorCodeEnum.ALREADLYANSWER);
                return response;
            }
            //批量插入
            int i = userTestResultMapper.batchInsert(userTestResultList);
            //修改用户答题情况
            userInfo.setAnswerStatus(userInfo.getAnswerStatus()+1);
            userInfo.setThemeId(themeId);
            int isLottery  = 0 ;//0没有资格抽奖 1有资格抽奖
            JSONObject jsonObject = new JSONObject();
            int  isTrueNum = 0;//答题正确数
            if(i > 0 ){//添加成功
                response.setResult(ErrorCodeEnum.SUCCESS);
                for(UserTestResult userTestResult:userTestResultList){
                    String  targetAnswer = userTestResult.getAnswer();
                    String  testId = userTestResult.getTestId();
                    //判断用户答题情况
                    TestInfo testInfoResult = testInfoMapper.selectByPrimaryKey(testId);
                    if(null != testInfoResult){
                        String source = testInfoResult.getAnswer();//答案对比
                        if(StringOrderUtil.isScrambledString(source,targetAnswer)){//如果相等则 适合多选
                            isTrueNum = isTrueNum+1;
                        }
                    }
                }
                //获取出相关主题参数值
                ThemeActivity themeActivity = themeActivityMapper.selectByPrimaryKey(themeId);
                if(null != themeActivity){
                    int lotteryAnswerLimit = themeActivity.getLotteryAnswerLimit();//答对题目数 100%设置
                    int  testRandomNum = themeActivity.getTestRandomNum();//随机出题数
                    if(lotteryAnswerLimit >testRandomNum) lotteryAnswerLimit=testRandomNum;//防止哪些乱设置的人
                    int  lotteryAnswerLimitResult = lotteryAnswerLimit;//抽奖资格数=百分比*随机出题数
                    if(lotteryAnswerLimitResult <= isTrueNum){//不能按百分比进行比对 这样需要去查询一次库
                        isLottery =1;
                        userInfo.setLotteryTimes(1);
                    }
                }
            }
            wxUserInfoMapper.updateByPrimaryKeySelective(userInfo);
            jsonObject.put("isLottery",isLottery);
            jsonObject.put("isTrueNum",isTrueNum);
            response.setResultV1(jsonObject);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

    @Override
    public Response randomSelectTestList(String wxUserId) {
        Response response = new Response();
        try{
            //随机获取当前themeId 并返回
            ThemeActivity themeActivityParam = new ThemeActivity();
            String startTime = DateUtil.getDateTime(new Date());
            themeActivityParam.setStartTime(startTime);
            themeActivityParam.setStatus(1);
            themeActivityParam.setStartSize(0);
            themeActivityParam.setEndSize(1);
            //themeActivityParam.setEndTime(endTime);
            List<ThemeActivity> themeActivityDateList =   themeActivityMapper.selectThemeActivityByDate(themeActivityParam);
            if(null == themeActivityDateList || themeActivityDateList.size() == 0){
                response.setResult(ErrorCodeEnum.ACTIVITYISOVER);
                return response;
            }
            String  themeId  = themeActivityDateList.get(0).getId();
            //查询出该用户是否已经答题
            WxUserInfo record = new WxUserInfo();
            record.setId(wxUserId);
           // record.setThemeId(themeId);
            WxUserInfo userInfo = wxUserInfoMapper.selectWxUserInfoSelective(record);
            int  isAnswer    = 0;
            if(themeId.equals(userInfo.getThemeId()) ){//之前答过一次题 才会存在相同的themeId
                isAnswer    = userInfo.getAnswerStatus();
            }
            int  answerTimes = themeActivityDateList.get(0).getAnswerTimes();//答题次数
            if(isAnswer > 0 && answerTimes <= isAnswer ){//表示已经答题
                response.setResult(ErrorCodeEnum.ALREADLYANSWER);
                return response;
            }
            //随机抽取题 获取这个主题下所有考试题 暂时先这样写 后面量大了再优化

            //获取主题随机出题数
            int  testRandomNum = 5;//随机出题数
            ThemeActivity themeActivity = themeActivityMapper.selectByPrimaryKey(themeId);
            if(null != themeActivity){
                testRandomNum = themeActivity.getTestRandomNum();
            }
            TestInfo testInfo = new TestInfo();
            testInfo.setEndSize(testRandomNum);
            List<TestInfo> list = testInfoMapper.randomTestInfoList(testInfo);
//            //随机抽取几套题
//            Random random = new Random();
//            int  checkTestRandomNumCount = 0;
//            boolean isEnd = true;
//            Map<String,String> existMap = new HashMap<>();
//            List<TestInfo> resultList = new ArrayList<>();//这里可以用set 为了方便统一用list
//            if(null != list && list.size() > 0){
//                if(list.size() <= testRandomNum){//如果题库数据不足时 抽出的数据永远小于等于设置的随机出题数时 将全部题加入
//                    resultList = list;
//                }else{
//                    while(isEnd){
//                        int  indexValue = random.nextInt(list.size());
//                        TestInfo testInfoA = list.get(indexValue);
//                        if(existMap.containsKey(existMap)){
//                            continue;
//                        }
//                        resultList.add(testInfoA);
//                        checkTestRandomNumCount +=1;
//                        if(checkTestRandomNumCount == testRandomNum){//当添加数据达到主题设置的随机数出题数时就退出
//                            isEnd = false;
//                        }
//                        existMap.put(testInfoA.getId(),testInfoA.getId());
//                    }
//                }
//
//            }
            JSONObject result = new JSONObject();
            result.put("testInfoList",list);
            result.put("themeId",themeId);
            result.put("themeDescription",themeActivity.getDescribe());
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

    @Override
    public Response queryThemeActivityList(BaseModel baseModel) {
        Response response = new Response();
        try{
            int      pageSize  = baseModel.getPageSize();
            int      pageIndex = baseModel.getPageIndex();
            int      startSize = (pageIndex -1)*pageSize;

            ThemeActivity userLotteryRecord = new ThemeActivity();
            userLotteryRecord.setEndSize(pageSize);
            userLotteryRecord.setStartSize(startSize);
            List<ThemeActivity> list = themeActivityMapper.selectThemeActivityByDate(userLotteryRecord);
            JSONObject result = new JSONObject();
            result.put("list",list);
            result.put("total",themeActivityMapper.selectThemeActivityByDateCount(userLotteryRecord));
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }
    @Override
    public Response queryTestInfoList(TestInfoModel baseModel) {
        Response response = new Response();
        try{
            int      pageSize  = baseModel.getPageSize();
            int      pageIndex = baseModel.getPageIndex();
            int      startSize = (pageIndex -1)*pageSize;

            TestInfo userLotteryRecord = new TestInfo();
            userLotteryRecord.setEndSize(pageSize);
            userLotteryRecord.setStartSize(startSize);
            userLotteryRecord.setTitle(baseModel.getTitle());
            List<TestInfo> list = testInfoMapper.queryTestInfoList(userLotteryRecord);
            JSONObject result = new JSONObject();
            result.put("list",list);
            result.put("total",testInfoMapper.queryTestInfoListCount(userLotteryRecord));
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteTestInfo(TestInfo testInfo) {
        Response response = new Response();
        try{
            testInfo.setStatus(0);
            int i = testInfoMapper.updateByPrimaryKeySelective(testInfo);
            if(i > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
                return response;
            }
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteThemeActivity(ThemeActivity themeActivity) {
        Response response = new Response();
        try{
            themeActivity.setStatus(2);
            int i = themeActivityMapper.updateByPrimaryKeySelective(themeActivity);
            if(i > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
                return response;
            }
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

    @Override
    public Response queryThemeActivity(ThemeActivity themeActivity) {
        Response response = new Response();
        try{
            String  id = themeActivity.getId();
            ThemeActivity testInfoResult = themeActivityMapper.selectByPrimaryKey(id);
            response.setResultV1(testInfoResult);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
    }

}

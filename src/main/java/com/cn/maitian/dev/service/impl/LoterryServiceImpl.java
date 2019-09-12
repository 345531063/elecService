package com.cn.maitian.dev.service.impl;

import com.cn.maitian.dev.constant.AwardsLevelEnum;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.dao.UserLotteryRecordMapper;
import com.cn.maitian.dev.dao.WxUserInfoMapper;
import com.cn.maitian.dev.entity.*;
import com.cn.maitian.dev.model.BaseModel;
import com.cn.maitian.dev.model.UserLotteryModel;
import com.cn.maitian.dev.service.LoterryService;
import com.cn.maitian.dev.service.TestQuestionService;
import com.cn.maitian.dev.util.LogUtil;
import com.cn.maitian.dev.util.StrUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LoterryServiceImpl implements LoterryService {
    @Autowired
    TestQuestionService testQuestionService;
    @Autowired
    UserLotteryRecordMapper userLotteryRecordMapper;
    @Autowired
    WxUserInfoMapper wxUserInfoMapper;
    /**** 
    * @Description: 开始抽奖 
    * @Param: 用户编号和活动编号必须传
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @Override
    public Response startLottery(UserLotteryRecord themeActivity) {
        Response response = new Response();
        try{
            //判断其抽奖资格
            WxUserInfo wxUserInfoParam = new WxUserInfo();
            wxUserInfoParam.setThemeId(themeActivity.getThemeId());
            wxUserInfoParam.setId(themeActivity.getWxUserId());
            WxUserInfo  wxUserInfo =wxUserInfoMapper.selectWxUserInfoSelective(wxUserInfoParam);
            if(null == wxUserInfo|| wxUserInfo.getLotteryTimes() == 0){
                response.setResult(ErrorCodeEnum.NOTLOTTERY);
                return  response;
            }
            UserLotteryModel userLotteryModel = new UserLotteryModel();
            userLotteryModel.setStartSize(0);
            userLotteryModel.setEndSize(1);
            userLotteryModel.setThemeId(themeActivity.getThemeId());
            userLotteryModel.setWxUserId(themeActivity.getWxUserId());
            List<UserLotteryRecord> userLotteryRecordList = userLotteryRecordMapper.selectUserLotteryRecord(userLotteryModel);
            //判断用户是否已经抽过一次奖 如果抽过一次 因为客户只能抽一次 则返回
            if(0 <  userLotteryRecordList.size()){
                JSONObject result = new JSONObject();
                result.put("level",userLotteryRecordList.get(0).getLotteryType());
                result.put("levelDescribe", AwardsLevelEnum.getAwardsLevelEnum(userLotteryRecordList.get(0).getLotteryType()).getDescription());
                response.setResult(ErrorCodeEnum.ISALREADLYLOTTERY);
                return  response;
            }

            wxUserInfo = wxUserInfoMapper.selectByPrimaryKey(themeActivity.getWxUserId());
            int  level = new Random().nextInt(100);//1%的几率中奖 一百个数中抽取值5个奖项
            int lotteryType = AwardsLevelEnum.getAwardsLevelEnum(level).getId();
            //如果用户没有抽过奖 则记录
            themeActivity.setOpenId(wxUserInfo.getOpenId());
            themeActivity.setPhone(wxUserInfo.getPhone());
            themeActivity.setNickname(wxUserInfo.getWxNickname());
            themeActivity.setLotteryType(lotteryType);//随机抽奖
            themeActivity.setId(StrUtils.generate(""));
            userLotteryRecordMapper.insert(themeActivity);
            //更新抽奖次数
            wxUserInfo.setLotteryTimes(0);
            wxUserInfoMapper.updateByPrimaryKey(wxUserInfo);
            JSONObject result = new JSONObject();
            result.put("level",lotteryType);
            result.put("levelDescribe", AwardsLevelEnum.getAwardsLevelEnum(level).getDescription());
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),"抽象方法异常"+e.getMessage());
        }
        return response;
    }
    /**
     * 抽奖记录查询
     * */
    @Override
    public Response queryLotteryList(UserLotteryModel baseModel) {
        Response response = new Response();
        try{
            int      pageSize  = baseModel.getPageSize();
            int      pageIndex = baseModel.getPageIndex();
            int      startSize = (pageIndex -1)*pageSize;

            baseModel.setEndSize(pageSize);
            baseModel.setStartSize(startSize);
            //判断其抽奖资格
            List<UserLotteryRecord> list = userLotteryRecordMapper.selectUserLotteryRecord(baseModel);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list",list);
            jsonObject.put("total",userLotteryRecordMapper.selectUserLotteryRecordCount(baseModel));
            response.setResultV1(jsonObject);
        }catch (Exception e){
            LogUtil.error(this.getClass(),"queryLotteryList 异常 "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response modifyLotteryInfo(UserLotteryRecord testInfo) {
        Response response = new Response();
        try{
            int i  = userLotteryRecordMapper.updateByPrimaryKeySelective(testInfo);
            if(i  > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
            }
        }catch (Exception e){
            LogUtil.error(this.getClass(),"queryLotteryList 异常 "+e.getMessage());
        }
        return response;
    }


}

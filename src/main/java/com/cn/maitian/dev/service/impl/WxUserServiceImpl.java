package com.cn.maitian.dev.service.impl;

import com.cn.maitian.dev.constant.AwardsLevelEnum;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.dao.*;
import com.cn.maitian.dev.entity.*;
import com.cn.maitian.dev.model.TestInfoModel;
import com.cn.maitian.dev.model.UserInfoModel;
import com.cn.maitian.dev.model.UserLotteryModel;
import com.cn.maitian.dev.service.WxUserService;
import com.cn.maitian.dev.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    BackendUserMapper backendUserMapper;
    @Autowired
    WxUserInfoMapper wxUserInfoMapper;

    @Autowired
    CompanyInfoMapper companyInfoMapper;

    @Autowired
    TestInfoMapper testInfoMapper;
    @Autowired
    UserLotteryRecordMapper userLotteryRecordMapper;
    @Autowired
    ThemeActivityMapper themeActivityMapper;
    @Override
    public Response queryUserLotteryQualification(WxUserInfo testInfo) {
       return null;
    }

    @Override
    public Response wxUserInfoRegister(WxUserInfo testInfo) {

        return null;
    }

    @Override
    public Response backendLogin(String loginPhone, String pwd, HttpServletResponse responsex) {
        Response response = new Response();
        try{
            responsex.setCharacterEncoding("utf-8");
            BackendUser backendUser  = new BackendUser();
            backendUser.setPhone(loginPhone);
            backendUser.setPwd(pwd);
            BackendUser backendUser1 = backendUserMapper.selectByInselective(backendUser);
            if(null == backendUser1){//没有此用户
                response.setResult(ErrorCodeEnum.NOUSERINFO);
                return response;
            }
            String token = StrUtils.generate("");
            String freshToken = StrUtils.generate("");
            CookieUtil.setCookieValueIntoResponse(responsex,token);
            JSONObject result = new JSONObject();
            result.put("token",token);
            result.put("freshToken",freshToken);
            response.setResultV1(result);

            //responsex.getWriter().write(JSONObject.fromObject(response).toString());
        }catch (Exception e){
            LogUtil.error(this.getClass(),"抽象方法异常"+e.getMessage());
        }
       return response;
    }
    /**** 
    * @Description: 微信登录则返回一个wxUserId
    * @Param: [loginPhone, jobNum, companyId] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/11 
    */
    @Override
    public Response wxLogin(String wxNickname, String jobNum, String companyId) {
        Response response = new Response();
        try{
            WxUserInfo wxUserInfo = new WxUserInfo();
            wxUserInfo.setJobNum(jobNum);
            wxUserInfo.setWxNickname(wxNickname);
             wxUserInfo = wxUserInfoMapper.selectWxUserInfoSelective(wxUserInfo);
            String id = null;
            if(null == wxUserInfo){//如果不存在就新增
//                wxUserInfo = new WxUserInfo();
//                id = StrUtils.generate("");
//                wxUserInfo.setId(id);
//                wxUserInfo.setPhone(loginPhone);
//                wxUserInfo.setJobNum(jobNum);
//                wxUserInfo.setCompanyId(companyId);
//                wxUserInfoMapper.insert(wxUserInfo);
                  response.setResult(ErrorCodeEnum.USERNOTEXIST);
                  return response;
            }
            id = wxUserInfo.getId();
            JSONObject result = new JSONObject();
            result.put("wxUserId",id);
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),"抽象方法异常"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response queryCompanyList() {
        Response response = new Response();
        try{
            List<CompanyInfo> list = companyInfoMapper.selectCompanyAll();
            JSONObject result = new JSONObject();
            result.put("list",list);
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),"抽象方法异常"+e.getMessage());
        }
        return response;
    }
    /**** 
    * @Description: 导入文件
    * @Param: [file] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/12 
    */
    @Override
    @Transactional
    public Response importWxUserInfo(MultipartFile file) {
        String fileName = file.getOriginalFilename();// 获取文件名
        Response response = new Response();
//        List<Map<String, Object>> userList = new LinkedList<Map<String, Object>>();
        try {
            if (!ExecuteDataExcelToOracle.validateExcel(fileName)) {// 验证文件名是否合格
                response.setResult(ErrorCodeEnum.ISNOTEXCELFILE);
                return response;
            }
            String extString = fileName.substring(fileName.lastIndexOf("."));
            List<WxUserInfo> list = ExecuteDataExcelToOracle.loadExcel(extString,file.getInputStream());
            //清空之间的数据
            wxUserInfoMapper.deleteAll();
            int i = wxUserInfoMapper.batchInsert(list);
            if(i > 0 ){
                response.setResult(ErrorCodeEnum.SUCCESS);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入数据库失败");
        }
        return response;
    }

    @Override
    public Response queryBackendUserList(UserInfoModel userInfoModel) {

        Response response = new Response();
        try{
            int      pageSize  = userInfoModel.getPageSize();
            int      pageIndex = userInfoModel.getPageIndex();
            int      startSize = (pageIndex -1)*pageSize;

            WxUserInfo userLotteryRecord = new WxUserInfo();
            userLotteryRecord.setEndSize(pageSize);
            userLotteryRecord.setStartSize(startSize);
            userLotteryRecord.setWxNickname(userInfoModel.getWxNickname());
            userLotteryRecord.setJobNum(userInfoModel.getJobNum());
            List<WxUserInfo> list = wxUserInfoMapper.selectUserInfoList(userLotteryRecord);
            JSONObject result = new JSONObject();
            result.put("list",list);
            result.put("total",wxUserInfoMapper.selectUserInfoListCount(userLotteryRecord));
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),e.getMessage());
        }
        return response;
        }

    @Override
    public Response userQualification(TestInfoModel wxUserInfo) {
        Response response = new Response();
        try {
            //随机获取当前themeId 并返回
            ThemeActivity themeActivityParamA = new ThemeActivity();
            String startTime = DateUtil.getDateTime(new Date());
            themeActivityParamA.setStartTime(startTime);
            themeActivityParamA.setStatus(1);
            themeActivityParamA.setStartSize(0);
            themeActivityParamA.setEndSize(1);
            //themeActivityParam.setEndTime(endTime);
            List<ThemeActivity> themeActivityDateList =   themeActivityMapper.selectThemeActivityByDate(themeActivityParamA);
            if(null == themeActivityDateList || themeActivityDateList.size() == 0){
                response.setResult(ErrorCodeEnum.ACTIVITYISOVER);
                return response;
            }
            String  themeId  = themeActivityDateList.get(0).getId();
            //查询出该用户是否已经答题
            WxUserInfo record = new WxUserInfo();
            record.setId(wxUserInfo.getWxUserId());
            //record.setThemeId(themeId);
            WxUserInfo userInfo = wxUserInfoMapper.selectWxUserInfoSelective(record);
            int  isAnswer = 0;//用户答题次数
            int  isLottery = 0;
            if(null != userInfo && themeId.equals(userInfo.getThemeId())){//表示答过题
                isAnswer = userInfo.getAnswerStatus();
                isLottery = userInfo.getLotteryTimes();
            }
            UserLotteryModel userLotteryModel  = new UserLotteryModel();
            userLotteryModel.setWxUserId(userInfo.getId());
            userLotteryModel.setThemeId(themeId);
            userLotteryModel.setStartSize(0);
            userLotteryModel.setEndSize(1);
            List<UserLotteryRecord> list = userLotteryRecordMapper.selectUserLotteryRecord(userLotteryModel);
            int  answerTimes = themeActivityDateList.get(0).getAnswerTimes();//答题次数

            JSONObject result = new JSONObject();
            result.put("remainderAnswer",answerTimes-isAnswer);//剩余次数
            result.put("isLottery",isLottery > 0 ? 1:0);//是否可抽奖 0 不可以 1 可以
            result.put("alreadyLottery",list.size()>0 ? 1 : 0);//是否已抽奖 0 没有 1 已经抽奖

            result.put("themeId",themeActivityDateList.get(0).getId());
            response.setResultV1(result);
        }catch (Exception e){
            LogUtil.error(this.getClass(),"queryUserLotteryQualification 异常"+e.getMessage());
        }
        return response;
    }

}

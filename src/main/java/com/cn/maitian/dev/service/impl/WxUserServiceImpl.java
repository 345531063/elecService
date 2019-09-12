package com.cn.maitian.dev.service.impl;

import com.cn.maitian.dev.constant.AwardsLevelEnum;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.dao.BackendUserMapper;
import com.cn.maitian.dev.dao.CompanyInfoMapper;
import com.cn.maitian.dev.dao.WxUserInfoMapper;
import com.cn.maitian.dev.entity.BackendUser;
import com.cn.maitian.dev.entity.CompanyInfo;
import com.cn.maitian.dev.entity.UserLotteryRecord;
import com.cn.maitian.dev.entity.WxUserInfo;
import com.cn.maitian.dev.service.WxUserService;
import com.cn.maitian.dev.util.Constants;
import com.cn.maitian.dev.util.ExecuteDataExcelToOracle;
import com.cn.maitian.dev.util.LogUtil;
import com.cn.maitian.dev.util.StrUtils;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @Override
    public Response queryUserLotteryQualification(WxUserInfo testInfo) {

        return null;
    }

    @Override
    public Response wxUserInfoRegister(WxUserInfo testInfo) {

        return null;
    }

    @Override
    public Response backendLogin(String loginPhone, String pwd) {
        Response response = new Response();
        try{
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
            Constants.TOKENMAP.put(backendUser1.getId(),token);
            JSONObject result = new JSONObject();
            result.put("token",token);
            result.put("freshToken",freshToken);
            response.setResultV1(result);
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
    public Response wxLogin(String loginPhone, String jobNum, String companyId) {
        Response response = new Response();
        try{
            WxUserInfo wxUserInfo = new WxUserInfo();
            wxUserInfo.setJobNum(jobNum);
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
            int status = wxUserInfoMapper.deleteAll();
            if(status > 0 ){
                int i = wxUserInfoMapper.batchInsert(list);
                if(i > 0 ){
                    response.setResult(ErrorCodeEnum.SUCCESS);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("插入数据库失败");
        }
        return response;
    }

}

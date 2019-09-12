package com.cn.maitian.dev.service;

import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.TestInfo;
import com.cn.maitian.dev.entity.WxUserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface WxUserService {

    /***
     * @Description: 查询抽奖资格
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response queryUserLotteryQualification (WxUserInfo testInfo);
    /*** 
    * @Description: 添加用户信息
    * @Param:  
    * @return:  
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    public Response wxUserInfoRegister (WxUserInfo testInfo);
    /**
     * 后台登录
     * */
    Response backendLogin(String loginPhone, String pwd);

    Response wxLogin(String loginPhone, String jobNum, String companyId);

    Response queryCompanyList();

    Response importWxUserInfo(MultipartFile file);
}

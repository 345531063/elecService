package com.cn.maitian.dev.controller;

import com.alibaba.druid.util.StringUtils;
import com.cn.maitian.dev.annotation.CheckLogin;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.ThemeActivity;
import com.cn.maitian.dev.entity.UserLotteryRecord;
import com.cn.maitian.dev.model.BaseModel;
import com.cn.maitian.dev.model.UserLotteryModel;
import com.cn.maitian.dev.service.LoterryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dfny")
public class LotteryController {
    @Autowired
    LoterryService loterryService;
    @RequestMapping("/lottery/startLottery")
    public Response startLottery(@RequestBody UserLotteryRecord baseModel){
        Response response = new Response();
        String wxUserId      = baseModel.getWxUserId();
        String themeId       = baseModel.getThemeId();
        String  companyId    = baseModel.getCompanyId();
        if(StringUtils.isEmpty(wxUserId) || StringUtils.isEmpty(themeId)||
        StringUtils.isEmpty(companyId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = loterryService.startLottery(baseModel);
        return response;
    }
    @RequestMapping("/lottery/queryLotteryList")
    @CheckLogin(value=true)
    public Response queryLotteryList(@RequestBody UserLotteryModel baseModel){
        Response response = new Response();
        response = loterryService.queryLotteryList(baseModel);
        return response;
    }
    /**** 
    * @Description: 修改抽奖记录
    * @Param: [baseModel] 
    * @return: com.cn.maitian.dev.constant.Response 
    * @Author: steven.zhang
    * @Date: 2019/9/12 
    */
    @RequestMapping("/lottery/modifyLottery")
    @CheckLogin(value=true)
    public Response modifyLottery(@RequestBody UserLotteryRecord userLotteryRecord){
        Response response = new Response();
        response = loterryService.modifyLotteryInfo(userLotteryRecord);
        return response;
    }
}

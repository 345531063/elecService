package com.cn.maitian.dev.service;

import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.UserLotteryRecord;
import com.cn.maitian.dev.model.BaseModel;
import com.cn.maitian.dev.model.UserLotteryModel;

public interface LoterryService {


    /***
     * @Description: 开始抽奖
     * @return: Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response startLottery(UserLotteryRecord themeActivity);
    /***
     * @Description: 抽奖列表
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response queryLotteryList(UserLotteryModel baseModel);

    /***
     * @Description: 修改抽奖信息
     * @Param: [testInfo]
     * @return: com.cn.maitian.dev.constant.Response
     * @Author: steven.zhang
     * @Date: 2019/9/10
     */
    public Response modifyLotteryInfo(UserLotteryRecord testInfo);

    Response queryLotteryInfo(UserLotteryModel themeActivity);
}

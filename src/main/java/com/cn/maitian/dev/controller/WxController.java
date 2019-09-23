package com.cn.maitian.dev.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.cn.maitian.dev.annotation.CheckLogin;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.entity.*;
import com.cn.maitian.dev.model.TestInfoModel;
import com.cn.maitian.dev.model.UserInfoModel;
import com.cn.maitian.dev.service.WxUserService;
import com.cn.maitian.dev.util.NetUtil;
import com.cn.maitian.dev.util.R;
import com.cn.maitian.dev.util.WXPublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "dfny")
@CrossOrigin
public class WxController {

    @Autowired
    WxUserService wxUserService;

    private static Logger log = LoggerFactory.getLogger(WxController.class);
    /*** 
    * @Description: 微信公众号服务器配置token验证 
    * @Param: [request] 
    * @return: java.lang.String 
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @RequestMapping("/wx/verifyWxToken")
    public String verifyWXToken(HttpServletRequest request){
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println("======msgSignature:"+msgSignature);
        System.out.println("======msgTimestamp:"+msgTimestamp);
        System.out.println("======msgNonce:"+msgNonce);
        System.out.println("======echostr:"+echostr);
        try{
            if (WXPublicUtil.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
                return echostr;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return echostr;
    }
    /*** 
    * @Description: 获取微信公众号token core_token
    * @Param:  
    * @return:  
    * @Author: steven.zhang
    * @Date: 2019/9/10 
    */
    @RequestMapping("/wx/queryWxCoreAccessToken")
    public String queryWxCoreAccessToken(HttpServletRequest request){

        return null;
    }
    /*** 
    * @Description: 登录
    * @Param:  
    * @return:  
    * @Author: steven.zhang
    * @Date: 2019/9/11 
    */
    @RequestMapping("/wx/wxLogin")
    public Response wxLogin(@RequestBody WxUserInfo wxUserInfo){
        Response response = new Response();
        String   nickname           = wxUserInfo.getWxNickname();
        String    jobNum              = wxUserInfo.getJobNum();
        String   companyId            = wxUserInfo.getCompanyId();
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(jobNum)
                || StringUtils.isEmpty(companyId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = wxUserService.wxLogin(nickname,jobNum,companyId);
        return response;
    }
    /***
     * @Description: 抽奖资格
     * @Param:
     * @return:
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/wx/userQualification")
    public Response userQualification(@RequestBody TestInfoModel wxUserInfo){
        Response response = new Response();
        String  wxUserId = wxUserInfo.getWxUserId();
        if(StringUtils.isEmpty(wxUserId)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = wxUserService.userQualification(wxUserInfo);
        return response;
    }
    /****
    * @Description: 导入用户信息
    * @Param:
    * @return:
    * @Author: steven.zhang
    * @Date: 2019/9/12
    */
    //导入excel
    @RequestMapping(value = "/importWxUserInfo", method= RequestMethod.POST)
    @CheckLogin(value=true)
    @ResponseBody
    public Response importWxUserInfo(@RequestParam(value="file",required = false) MultipartFile file){
        Response response = new Response();
        if(null == file ){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = wxUserService.importWxUserInfo(file);
        return response;
    }
    /***
     * @Description: 登录
     * @Param:
     * @return:
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/wx/queryBackendUserList")
    public Response queryBackendUserList(@RequestBody UserInfoModel userInfoModel){
        Response response = new Response();
        response = wxUserService.queryBackendUserList(userInfoModel);
        return response;
    }
    /***
     * @Description: 登录
     * @Param:
     * @return:
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/wx/queryCompanyList")
    public Response queryCompanyList(){
        Response response = new Response();
        response = wxUserService.queryCompanyList();
        return response;
    }
    /***
     * @Description: 用户登录
     * @Param:
     * @return:
     * @Author: steven.zhang
     * @Date: 2019/9/11
     */
    @RequestMapping("/backend/login")
    public Response backendLogin(@RequestBody  BackendUser backendUser, HttpServletResponse responsex){
        Response response = new Response();
        String   loginPhone           = backendUser.getPhone();
        String    pwd                 = backendUser.getPwd();
        if(null == loginPhone || "".equals(loginPhone)||null == pwd ||"".equals(pwd)){
            response.setResult(ErrorCodeEnum.PARAMISERROR);
            return  response;
        }
        response = wxUserService.backendLogin(loginPhone,pwd,responsex);
        return response;
    }
    @RequestMapping(value = "/setCookies",method = RequestMethod.GET)
    public String setCookies(HttpServletResponse response){
        //HttpServerletRequest 装请求信息类
        //HttpServerletRespionse 装相应信息的类
        Cookie cookie=new Cookie("sessionId","CookieTestInfo");
        response.addCookie(cookie);
        return "获得cookies信息成功";
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     *
     * 用户同意授权，获取code
     */
    @RequestMapping("/authorize")
    @ResponseBody
    public static R authorize() {
        String appid = "wxbb000000000e";
        //String uri ="wftest.zzff.net/wx/weixinLogin";
        String uri = urlEncodeUTF8("wftest.zzff.net/api/wx/weixinLogin");
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+uri+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line =null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /*  com.alibaba.fastjson.JSONObject jsonObj= FastJSONUtils.getJSONObject(result);
                String access_token = jsonObj.getString("access_token");
                long expires_in = Long.valueOf(jsonObj.getString("expires_in"));
            */
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return  R.ok(result);
    }

    @RequestMapping("/weixinLogin")
    @ResponseBody
    public void weixinLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 用户同意授权后，能获取到code
        Map<String, String[]> params = request.getParameterMap();//针对get获取get参数
        String[] codes = params.get("code");//拿到code的值
        String code = codes[0];//code
        //String[] states = params.get("state");
        //String state = states[0];//state

        System.out.println("****************code:"+code);
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            Oauth2Token oauth2Token = getOauth2AccessToken("wxb0000000000e", "4c22222233333335555a9", code);
            System.out.println("***********************************oauth2Token信息："+oauth2Token.toString());
            // 网页授权接口访问凭证
            String accessToken = oauth2Token.getAccessToken();
            // 用户标识
            String openId = oauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = getSNSUserInfo(accessToken, openId);
            System.out.println("***********************************用户信息unionId："+snsUserInfo.getUnionid()+"***:"+snsUserInfo.getNickname());
            // 设置要传递的参数

            //具体业务start

            //具体业务end

            String url = "http://wftest.zzff.net/#/biddd?from=login&tokenId="+snsUserInfo.getUnionid();

            response.sendRedirect(url);
            return ;
        }
    }


    /**
     * 获取网页授权凭证
     *
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static Oauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        Oauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));
        if (null != jsonObject) {
            try {
                wat = new Oauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        com.alibaba.fastjson.JSONObject jsonObject =  JSON.parseObject(NetUtil.get(requestUrl));

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                List<String> list = JSON.parseArray(jsonObject.getString("privilege"),String.class);
                snsUserInfo.setPrivilegeList(list);
                //与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
                snsUserInfo.setUnionid(jsonObject.getString("unionid"));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}

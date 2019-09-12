package com.cn.maitian.dev.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.cn.maitian.dev.annotation.CheckLogin;
import com.cn.maitian.dev.constant.ErrorCodeEnum;
import com.cn.maitian.dev.constant.Response;
import com.cn.maitian.dev.util.CookieUtil;
import com.cn.maitian.dev.util.R;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheckInterceptor implements HandlerInterceptor {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先查看服务器端是否存在cookie对应的session
        Response responseRsult = new Response();
        response.setCharacterEncoding("UTF-8");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckLogin checkLogin = handlerMethod.getMethod().getAnnotation(CheckLogin.class);
        boolean value = checkLogin.value();
        if(value){//需要验证
            String cookie = CookieUtil.getCookieValueFromRequest(request);
            if(StringUtils.isEmpty(cookie)){
                responseRsult.setResult(ErrorCodeEnum.NONLOGIN);
                response.getWriter().println(JSONObject.fromObject(responseRsult).toString());
                return false;
            }
        }

        return  true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
 
    }
}

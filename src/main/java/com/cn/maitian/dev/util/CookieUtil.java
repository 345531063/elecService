package com.cn.maitian.dev.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
 
    public static final String COOKIE_NAME = "code";
 
    public static String generateCookieByUserId(Integer userId, Integer type) {
        Long currentTime = System.currentTimeMillis();
        return currentTime + "" + userId + "" + type;
    }
 
    public static String getCookieValueFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
 
    public static void setCookieValueIntoResponse(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        response.addCookie(cookie);
    }
}
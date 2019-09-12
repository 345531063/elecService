package com.cn.maitian.dev.util;

public class RedisKeyUtil {
    static  String sperator = ":";
    public static String getUserNonceKey(String userId, String nonce) {
        return "userId"+sperator+userId+sperator+"nonce"+sperator+nonce;
    }

    public static String getUserAccessTokenKey(String userId, String accessToken) {
        return "userId"+sperator+userId+sperator+"accessToken"+sperator;
    }
    public static String getUserFreshAccessTokenKey(String userId, String accessToken) {
        return "userId"+sperator+userId+sperator+"freshAccessToken"+sperator;
    }
}

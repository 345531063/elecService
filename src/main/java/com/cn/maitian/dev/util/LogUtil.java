package com.cn.maitian.dev.util;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zxd on 2017/10/24.
 */
public class LogUtil {
    public static void info(Class<?> clazz,Object info){
        Logger logger = Logger.getLogger(clazz);
        try{
            logger.info(info.toString());
        }catch (Exception e){}
    }
    public static void error(Class<?> clazz,Object error){
        Logger logger = Logger.getLogger(clazz);
        try{
            logger.error(error.toString());
        }catch (Exception e){}
    }
    public static void printStackTraceLog(Class<?> clazz,Exception e){
         Logger logger = Logger.getLogger(clazz);
        try{
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String strs = sw.toString();
            logger.error(strs);
        }catch (Exception e2){}
    }
}

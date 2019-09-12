package com.cn.maitian.dev.annotation;

import java.lang.annotation.*;

/***
* @Description: 删除
* @Param:  
* @return:  
* @Author: steven.zhang
* @Date: 2019/9/12 
*/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CheckLogin {
    boolean value() default false;
}

package com.ymc.reggie.common;

/**
 * 基于ThreadLocal的封装工具类，用户保存和获取当前登录的用户id
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

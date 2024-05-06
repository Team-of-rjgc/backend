package com.gdut.lostfound.common.constant.annotation;

import com.gdut.lostfound.common.constant.enums.ActionEnum;

import java.lang.annotation.*;

/**
 * lost-found
 * com.gdut.backend.common.constant.annotation
 * 行为注解，用于记录日志
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:25 Monday
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionLog {
    /**
     * 服务码
     *
     * @see ServiceEnum
     */
    ServiceEnum service();

    /**
     * 动作（CURD）
     *
     * @see ActionEnum
     */
    ActionEnum action();

    /**
     * 目标名称
     */
    String targetName() default "";
}

package com.gdut.lostfound.common.constant.annotation;

import com.gdut.lostfound.common.constant.enums.ActionEnum;

import java.lang.annotation.*;


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

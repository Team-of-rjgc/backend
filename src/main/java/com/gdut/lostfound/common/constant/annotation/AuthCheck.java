package com.gdut.lostfound.common.constant.annotation;

import com.gdut.lostfound.common.constant.enums.UserKindEnum;

import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 用户级别
     */
    UserKindEnum level();

    /**
     * 匹配模式
     */
    MatchModeEnum mode() default MatchModeEnum.MIN;

    //String role() default "默认角色";
}

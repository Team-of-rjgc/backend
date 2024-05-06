package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LevelEnum implements EnumInter {
    SYSTEM(0, "系统级别"),
    SCHOOL(1, "学校级别"),
    CAMPUS(2, "校区级别"),
    PERSONAL(3, "个人级别"),
    MANAGER_ALL(4, "所有管理员"),
    USER_ALL(5, "所有普通用户"),//管理员除外的
    ;

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

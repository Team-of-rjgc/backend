package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserKindEnum implements EnumInter {
    STUDENT(0, "学生"),
    STAFF(1, "教职工"),
    MANAGER(2, "管理员"),//区别于普通用户
    SUPER_MANAGER(3, "超级管理员"),//系统管理员
    ;

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

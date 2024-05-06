package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum GenderEnum implements EnumInter {
    UNKNOWN(-1, "未知"),
    FEMALE(0, "女"),
    MALE(1, "男");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

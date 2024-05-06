package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EffectEnum implements EnumInter {
    INVALID(0, "无效"),
    VALID(1, "有效"),
    NOT_YET(2, "未开始"),
    EXPIRED(3, "已过期");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

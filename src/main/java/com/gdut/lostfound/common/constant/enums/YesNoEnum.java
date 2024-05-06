package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNoEnum implements EnumInter {
    NO(0, "否"),
    YES(1, "是");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

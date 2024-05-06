package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordStatusEnum implements EnumInter {
    DELETED(0, "已删除"),
    EXISTS(1, "存在");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

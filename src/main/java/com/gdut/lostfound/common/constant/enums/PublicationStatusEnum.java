package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum PublicationStatusEnum implements EnumInter {
    WAITING(0, "待审批"),
    FINDING(1, "寻回中"),//寻回中（审批通过）
    REJECTED(2, "驳回"),//驳回（审批不通过）
    CLAIMED(3, "已寻回"),
    CLOSED(4, "已关闭");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}


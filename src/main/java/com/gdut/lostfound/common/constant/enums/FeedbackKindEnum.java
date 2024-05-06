package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedbackKindEnum implements EnumInter {
    USAGE(0, "使用反馈"),
    REPORT(1, "举报"),
//    COMPLAIN(2, "投诉"),
    ;
    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }

}

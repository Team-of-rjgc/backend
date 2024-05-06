package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum NoticeKindEnum implements EnumInter {
    TEXT_NOTICE(0, "文字公告"),
    IMAGE_DISPLAY(1, "图片轮播");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

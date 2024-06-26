package com.gdut.lostfound.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ApplyKindEnum implements EnumInter {
    LOST_PUBLISH(0, "失物发布"),//寻物
    FOUND_PUBLISH(1, "认领发布"),//认领
    IDENTITY_VERIFY(2, "身份认证"),
    ITEM_CLAIM(3, "物品认领"),
    ACCOUNT_APPEAL(4, "账号申诉");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

package com.gdut.lostfound.common.constant.annotation;

import com.gdut.lostfound.common.constant.enums.EnumInter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionEnum implements EnumInter {
    CREATE(0, "新增"),
    READ(1, "查询"),
    UPDATE(2, "更新"),
    DELETE(3, "删除"),;
    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }

}

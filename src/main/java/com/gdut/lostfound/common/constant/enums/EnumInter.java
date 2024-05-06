package com.gdut.lostfound.common.constant.enums;

/**
 * lost-found
 * com.gdut.backend.common.constant.enums
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 18:30 Friday
 */
public interface EnumInter {
    /**
     * code
     */
    Integer getCode();

    /**
     * 描述
     */
    String getDesc();

    boolean equals(Integer code);
}

package com.gdut.lostfound.common.constant.exception;

import lombok.Getter;


@Getter
public class LostFoundException extends RuntimeException {
    /**
     * 错误码
     *
     * @see com.gdut.lostfound.common.constant.enums.ErrorEnum
     */
    private int code;

    public LostFoundException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}

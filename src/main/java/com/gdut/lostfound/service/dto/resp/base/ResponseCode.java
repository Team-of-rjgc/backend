package com.gdut.lostfound.service.dto.resp.base;

/**
 * @see com.gdut.lostfound.common.constant.enums.ErrorEnum
 */
public interface ResponseCode {
    /**
     * 成功success
     */
    int SUCCESS = 1000;
    /**
     * 异常Exception
     */
    int EXCEPTION = 1001;
    /**
     * 参数错误Parameter error
     */
    int PARAM_INVALID = 1002;

}

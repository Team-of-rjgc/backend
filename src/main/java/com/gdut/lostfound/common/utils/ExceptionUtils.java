package com.gdut.lostfound.common.utils;

import com.gdut.lostfound.common.constant.exception.LostFoundException;
import com.gdut.lostfound.common.constant.enums.ErrorEnum;
import org.slf4j.helpers.MessageFormatter;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionUtils {
    /**
     * 创建异常
     */
    public static Exception createException(ErrorEnum errorEnum, Object... args) {
        //占位符替换
        return new LostFoundException(errorEnum.getCode(), MessageFormatter.arrayFormat(errorEnum.getDesc(), args).getMessage());
    }

    /**
     * 获取异常的堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
package com.gdut.lostfound.common.utils;

import com.gdut.lostfound.common.constant.enums.EnumInter;
import com.gdut.lostfound.common.constant.enums.ErrorEnum;

/**
 * lost-found
 * com.gdut.backend.common.utils
 * 枚举工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 20:57 Monday
 */
public class EnumUtils {
    /**
     * 根据code从枚举中返回desc
     */
    public static String getDesc(Integer code, EnumInter[] inters) {
        for (EnumInter en : inters) {
            if (en.equals(code)) {
                return en.getDesc();
            }
        }
        return null;
    }

    /**
     * 从枚举中检验并返回code
     */
    public static Integer checkAndGetCode(Integer code, EnumInter[] inters) throws Exception {
        for (EnumInter en : inters) {
            if (en.equals(code)) {
                return code;
            }
        }
        throw ExceptionUtils.createException(ErrorEnum.NO_MATCH_ENUM, inters.getClass().getSimpleName(), code);
    }

    /**
     * 是否包含code
     */
    public static boolean containsCode(Integer code, EnumInter[] inters) {
        for (EnumInter en : inters) {
            if (en.equals(code)) {
                return true;
            }
        }
        return false;
    }
}

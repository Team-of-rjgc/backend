package com.gdut.lostfound.service.utils;

import com.gdut.lostfound.common.constant.enums.ErrorEnum;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.dao.entity.User;

import javax.servlet.http.HttpSession;

/**
 * lost-found
 * com.gdut.backend.service.utils
 * session工具类哦
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/16 19:35 Tuesday
 */
public class SessionUtils {
    public static User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static User checkAndGetUser(HttpSession session) throws Exception {
        User user = getUser(session);
        if (user == null) {
            throw ExceptionUtils.createException(ErrorEnum.USER_LOGIN_ERROR);
        }
        return user;
    }
}

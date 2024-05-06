package com.gdut.lostfound.service.utils;

import com.gdut.lostfound.common.constant.enums.ErrorEnum;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.dao.entity.User;

import javax.servlet.http.HttpSession;

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

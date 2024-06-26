package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.SetPasswordReq;
import com.gdut.lostfound.service.dto.req.StudentRecognizeReq;
import com.gdut.lostfound.service.dto.req.UserInfoListReq;
import com.gdut.lostfound.service.dto.req.UserLoginReq;
import com.gdut.lostfound.service.dto.resp.StudentRecognizeResp;
import com.gdut.lostfound.service.dto.resp.UserInfoListResp;
import com.gdut.lostfound.service.dto.resp.UserInfoResp;

import javax.servlet.http.HttpSession;

public interface UserService {
    /**
     * 认证登录
     */
    StudentRecognizeResp recognizeStudent(StudentRecognizeReq req, HttpSession session) throws Exception;

    /**
     * 激活用户
     */
    String activateUser(String code);

    /**
     * 用户登录
     */
    StudentRecognizeResp loginUser(UserLoginReq req, HttpSession session) throws Exception;

    /**
     * 查询单个用户信息
     */
    UserInfoResp userInfo(String userId) throws Exception;

    /**
     * 用户信息列表
     */
    UserInfoListResp userList(UserInfoListReq req, HttpSession session) throws Exception;

    /**
     * 冻结用户
     */
    void freezeUser(String userId, HttpSession session) throws Exception;

    /**
     * 解冻用户
     */
    void unfreezeUser(String userId) throws Exception;

    /**
     * 用户设置为管理员
     */
    void setAsAdmin(String userId, Integer flag) throws Exception;

    /**
     * 设置手机号
     */
    String setPhoneNumber(String phoneNumber, HttpSession session) throws Exception;

    /**
     * 修改头像
     */
    String setIcon(String icon, HttpSession session) throws Exception;

    /**
     * 修改密码
     */
    void setPassword(SetPasswordReq req, HttpSession session) throws Exception;

    /**
     * 重置密码
     */
    void resetPassword(String userId, HttpSession session) throws Exception;

}

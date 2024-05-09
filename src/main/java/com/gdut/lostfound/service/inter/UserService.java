package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.*;
import com.gdut.lostfound.service.dto.resp.StudentRecognizeResp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface UserService {
    /**
     * 向指定邮箱发送注册需要的验证码
     *
     * @param email 邮箱号
     */
    void sendRegisterMailCode(String email);


    /**
     * 向指定邮箱发送重置密码需要的验证码
     *
     * @param email 邮箱号
     */
    void sendResetMailCode(String email);

    /**
     * 注册
     *
     * @param userRegisterReq 认证用户请求信息
     * @return 是否成功
     */
    void register(UserRegisterReq userRegisterReq);

    /**
     * 登录
     *
     * @param req 登录请求信息
     * @param session 登录session
     * @return 登录结果
     */
    StudentRecognizeResp login(UserLoginReq req, HttpSession session) throws Exception;

    /**
     * 修改密码
     */
    void setPassword(SetPasswordReq req, HttpSession session) throws Exception;

    /**
     * 通过邮箱重置密码
     */
    void resetPassword(ResetPasswordReq resetPasswordReq) throws Exception;

    /**
     * 修改昵称
     */
    String setNickName(String nickName, HttpSession session) throws Exception;

    /**
     * 修改头像
     */
    String setIcon(MultipartFile image, HttpSession session) throws Exception;

//    /**
//     * 用户设置为管理员
//     */
//    void setAsAdmin(String userId, Integer flag) throws Exception;
//
//    /**
//     * 查询单个用户信息
//     */
//    UserInfoResp userInfo(String userId) throws Exception;
//
//    /**
//     * 用户信息列表
//     */
//    UserInfoListResp userList(UserInfoListReq req, HttpSession session) throws Exception;

}

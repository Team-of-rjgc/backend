package com.gdut.lostfound.web.controller;

import com.gdut.lostfound.common.constant.annotation.AuthCheck;
import com.gdut.lostfound.common.constant.enums.UserKindEnum;
import com.gdut.lostfound.common.constant.enums.YesNoEnum;
import com.gdut.lostfound.common.utils.EnumUtils;
import com.gdut.lostfound.service.dto.req.CategoryAddReq;
import com.gdut.lostfound.service.dto.req.UserInfoListReq;
import com.gdut.lostfound.service.dto.resp.base.ResponseDTO;
import com.gdut.lostfound.service.inter.CategoryService;
import com.gdut.lostfound.service.inter.UserService;
import com.gdut.lostfound.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/api/v1/admin")
@AuthCheck(level = UserKindEnum.MANAGER)
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看用户信息
     */
    @PostMapping("/userInfo")
    public ResponseDTO userInfo(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        return ResponseDTO.successObj("user", userService.userInfo(userId));
    }

    /**
     * 重置用户密码
     */
    @PostMapping("/resetPassword")
    public ResponseDTO resetPassword(@NotBlank(message = "用户id不能为空") @RequestParam String userId, HttpSession session)
            throws Exception {
        userService.resetPassword(userId, session);
        return ResponseDTO.successObj();
    }

    /**
     * 查看用户信息列表
     */
    @PostMapping("/userList")
    public ResponseDTO userInfo(@Valid @RequestBody UserInfoListReq req, HttpSession session) throws Exception {
        return ResponseDTO.successObj("page", userService.userList(req, session));
    }

    /**
     * 冻结用户
     */
    @PostMapping("/freezeUser")
    public ResponseDTO freezeUser(@NotBlank(message = "用户id不能空") @RequestParam String userId, HttpSession session)
            throws Exception {
        userService.freezeUser(userId, session);
        return ResponseDTO.successObj();
    }

    /**
     * 解冻用户
     */
    @PostMapping("/unfreezeUser")
    public ResponseDTO unfreezeUser(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        userService.unfreezeUser(userId);
        return ResponseDTO.successObj();
    }

    /**
     * 用户设置为管理员
     *
     * @param userId 用户id
     * @param flag   0：否，1：是
     * @see YesNoEnum
     */
    @PostMapping("/setAsAdmin")
    public ResponseDTO setAsAdmin(@NotBlank(message = "用户id不能空") @RequestParam String userId,
                                  @NotNull(message = "标志位不能空") @RequestParam Integer flag) throws Exception {
        EnumUtils.checkAndGetCode(flag, YesNoEnum.values());
        userService.setAsAdmin(userId, flag);
        return ResponseDTO.successObj();
    }


    /**
     * 新增类别
     */
    @PostMapping("/addCategory")
    public ResponseDTO addCategory(@Valid @RequestBody CategoryAddReq req, HttpSession session) throws Exception {
        categoryService.addCategory(req, SessionUtils.checkAndGetUser(session));
        return ResponseDTO.successObj();
    }

    /**
     * 删除类别
     */
    @PostMapping("/deleteCategory")
    public ResponseDTO addCategory(@NotBlank(message = "类别名不能为空") @RequestParam String name, HttpSession session)
            throws Exception {
        categoryService.deleteCategory(name, session);
        return ResponseDTO.successObj();
    }

}

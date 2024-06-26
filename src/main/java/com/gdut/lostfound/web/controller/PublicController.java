package com.gdut.lostfound.web.controller;

import com.gdut.lostfound.common.constant.annotation.ActionLog;
import com.gdut.lostfound.common.constant.annotation.ServiceEnum;
import com.gdut.lostfound.common.constant.annotation.ActionEnum;
import com.gdut.lostfound.common.constant.enums.ErrorEnum;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.service.dto.req.StudentRecognizeReq;
import com.gdut.lostfound.service.dto.req.UserLoginReq;
import com.gdut.lostfound.service.dto.resp.StudentRecognizeResp;
import com.gdut.lostfound.service.dto.resp.base.ResponseDTO;
import com.gdut.lostfound.service.inter.UserService;
import com.gdut.lostfound.service.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@Validated
@RequestMapping("/api/v1/public")
public class PublicController {

    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    /**
     * 验证码长度
     */
    private static final int codeLength = 5;

    /**
     * 生成随机验证码
     * http://localhost:8080//api/v1/common/verifyCode
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        String code = verifyCodeUtils.getRandomNum(codeLength);
        //将code存入session，用于后期校验
        session.setAttribute("code", code);
        BufferedImage image = verifyCodeUtils.getImage(code);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setDateHeader(HttpHeaders.EXPIRES, -1L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");//http 1.1
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");//http 1.0
        //将图片写入响应输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    @Autowired
    private UserService userService;

    /**
     * 认证/登录
     */
    @PostMapping("/recognize")
    @ResponseBody
    @ActionLog(service = ServiceEnum.USER_REG, action = ActionEnum.CREATE)
    public ResponseDTO studentRecognize(@Valid @RequestBody StudentRecognizeReq req, HttpSession session) throws Exception {
        checkVerifyCode(session, req.getCode());
        StudentRecognizeResp resp = userService.recognizeStudent(req, session);
        session.removeAttribute("code");
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 登录
     */
    @ActionLog(service = ServiceEnum.USER_LOGIN, action = ActionEnum.CREATE)
    @PostMapping("/login")
    @ResponseBody
    public ResponseDTO userLogin(@Valid @RequestBody UserLoginReq req, HttpSession session) throws Exception {
        checkVerifyCode(session, req.getCode());
        StudentRecognizeResp resp = userService.loginUser(req, session);
        session.removeAttribute("code");
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 激活账号
     */
    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@NotBlank(message = "激活码不能为空") @PathVariable("code") String code) {
        return "/static/activate/" + userService.activateUser(code);
    }


    /**
     * 校验验证码
     */
    private void checkVerifyCode(HttpSession session, String code) throws Exception {
        if (!code.equals(session.getAttribute("code"))) {
            throw ExceptionUtils.createException(ErrorEnum.VERIFY_CODE_ERROR);
        }
    }


}

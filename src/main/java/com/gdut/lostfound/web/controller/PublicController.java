package com.gdut.lostfound.web.controller;

import com.gdut.lostfound.common.constant.annotation.ActionLog;
import com.gdut.lostfound.common.constant.annotation.ServiceEnum;
import com.gdut.lostfound.common.constant.annotation.ActionEnum;
import com.gdut.lostfound.common.constant.enums.ErrorEnum;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.service.dto.req.ResetPasswordReq;
import com.gdut.lostfound.service.dto.req.UserLoginReq;
import com.gdut.lostfound.service.dto.req.UserRegisterReq;
import com.gdut.lostfound.service.dto.resp.StudentRecognizeResp;
import com.gdut.lostfound.service.dto.resp.base.ResponseDTO;
import com.gdut.lostfound.service.inter.UserService;
import com.gdut.lostfound.service.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@Validated
@RequestMapping("/api/v1/public")
@Slf4j
public class PublicController {

    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    /**
     * 验证码长度
     */
    private static final int codeLength = 5;


    @Autowired
    private UserService userService;

    @Value("${web.upload-path}")
    private String path;

    @GetMapping("/downloadImage")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("fileName") String fileName) throws IOException {
        File file = new File(path + "/" + fileName);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytesArray = new byte[(int) file.length()];
            fis.read(bytesArray);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(file.length());
            headers.setContentDispositionFormData("attachment", "image.jpg");

            return new ResponseEntity<>(bytesArray, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.warn("获取文件转为base64失败");
            return null;
        }

    }

    /**
     * 生成随机验证码(用于登录)
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


    /**
     * 获取注册需要的邮箱验证码
     *
     * @param email 邮箱
     */
    @ResponseBody
    @GetMapping(value = "/getRegisterEmailCode")
    public ResponseDTO getRegisterEmailCode(@RequestParam("email") String email) {
        userService.sendRegisterMailCode(email);
        return ResponseDTO.successObj();
    }


    /**
     * 获取重置密码需要的邮箱验证码
     *
     * @param email 邮箱
     */
    @ResponseBody
    @GetMapping(value = "/getResetEmailCode")
    public ResponseDTO getResetEmailCode(@RequestParam("email") String email) {
        userService.sendResetMailCode(email);
        return ResponseDTO.successObj();
    }

    /**
     *  通过邮箱注册
     * @param userRegisterReq 注册相关信息
     */
    @PostMapping(value = "/register")
    @ResponseBody
    @ActionLog(service = ServiceEnum.USER_REG, action = ActionEnum.CREATE)
    public ResponseDTO register(@RequestBody UserRegisterReq userRegisterReq) {
        userService.register(userRegisterReq);
        return ResponseDTO.successObj();
    }

    /**
     * 登录
     */
    @ActionLog(service = ServiceEnum.USER_LOGIN, action = ActionEnum.CREATE)
    @PostMapping("/login")
    @ResponseBody
    public ResponseDTO login(@Valid @RequestBody UserLoginReq req, HttpSession session) throws Exception {
        checkVerifyCode(session, req.getCode());
        StudentRecognizeResp resp = userService.login(req, session);
        return ResponseDTO.successObj("user", resp);
    }

    /**
     * 通过邮箱重置密码
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseDTO resetPassword(@Valid @RequestBody ResetPasswordReq resetPasswordReq) throws Exception {
        userService.resetPassword(resetPasswordReq);
        return ResponseDTO.successObj();
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

package com.gdut.lostfound.service.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Validated
@NoArgsConstructor
public class ResetPasswordReq {
    /**
     * 重置密码需要的验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email ;

    /**
     * 新的密码
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 64, message = "新密码长度必须在6-64")
    private String newPassword;

    /**
     * 确认新的密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 64, message = "新密码长度必须在6-64")
    private String confirmPassword;
}

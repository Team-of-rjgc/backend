package com.gdut.lostfound.service.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 认证用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterReq {
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 64, message = "密码长度必须在6-64")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;
}

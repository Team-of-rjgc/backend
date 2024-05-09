package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@NoArgsConstructor
public class SetPasswordReq {
    /**
     * 旧密码
     *
     */
    @NotBlank(message = "旧密码不能为空")
    @Length(max = 64)
    private String oldPassword;

    /**
     * 新密码
     *
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

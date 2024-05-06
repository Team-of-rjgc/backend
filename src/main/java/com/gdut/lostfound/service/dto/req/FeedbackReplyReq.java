package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@Validated
public class FeedbackReplyReq {
    @NotBlank
    private String id;

    @NotEmpty(message = "回复内容不能为空")
    @Length(min = 1, max = 1024, message = "回复内容长度在1-1024之间")
    private String content;
}

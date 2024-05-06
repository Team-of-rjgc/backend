package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 新增评论入参
 */
@Data
@NoArgsConstructor
@Validated
public class CommentAddReq {
    @NotBlank(message = "评论目标id不能为空")
    private String targetId;

    @NotBlank(message = "评论内容不能为空")
    @Length(max = 128, min = 1)
    private String content;
}

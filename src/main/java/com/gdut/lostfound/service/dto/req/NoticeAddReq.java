package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor//不加会报空请求体异常
@Validated
public class NoticeAddReq {
    @Length(min = 1, max = 128, message = "标题长度必须在1-128之间")
    private String title;

    @Length(min = 1, max = 1024, message = "内容长度必须在1-1024之间")
    private String content;

    @NotNull
    private Boolean fixTop;
}

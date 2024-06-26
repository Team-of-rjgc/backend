package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Validated
@NoArgsConstructor
public class UserInfoListReq {
    /**
     * 关键字
     */
    @NotNull
    private String keyword;
    /**
     * 页号（0开始）
     */
    @NotNull
    private Integer pageNum;

    @NotNull
    @Max(value = 100, message = "每页限制最大100条")
    @Min(value = 1, message = "每页限制最小1条")
    private Integer pageSize;
}

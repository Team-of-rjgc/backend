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
public class PublicationListReq {
    /**
     * 类型,不为0（失物发布）、1（认领发布）时，查询全部
     */
    @NotNull
    private Integer kind;
    /**
     * 类别，可为空字符串
     */
    @NotNull
    private String category;
    /**
     * title中的关键字
     */
    @NotNull
    private String keyword;

    /**
     * 页号
     */
    @NotNull
    private Integer pageNum;
    /**
     * 页大小
     */
    @NotNull
    @Max(value = 100, message = "每页限制最大100条")
    @Min(value = 1, message = "每页限制最小1条")
    private Integer pageSize;
}

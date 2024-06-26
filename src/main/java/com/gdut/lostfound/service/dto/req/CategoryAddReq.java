package com.gdut.lostfound.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Validated
public class CategoryAddReq {
    /**
     * 类型名
     */
    @NotBlank(message = "类型名称不能为空空")
    @Length(min = 1, max = 128, message = "类别名称长度必须在1-128之间")
    private String name;
    /**
     * 说明
     */
    @Length(min = 0, max = 256, message = "类别说明长度必须在0-256之间")
    private String about;
    /**
     * 图标
     */
//    private String image;
    /**
     * 创建人id
     */
//    private String creatorId;
    /**
     * 创建时间
     */
//    private Date createTime;
    /**
     * 级别类型：0：系统级别, 1：校园级别, 2：校区级别
     *
     * @see com.gdut.lostfound.common.constant.enums.LevelEnum
     */
//    private Integer level;
    /**
     * 作用对象id系统级别为“system”
     */
//    private String targetId;
}

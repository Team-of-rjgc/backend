package com.gdut.lostfound.service.dto.resp;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryListResp {
    /**
     * 类型名
     */
    private String name;
    /**
     * 说明
     */
    private String about;
    /**
     * 图标
     */
    private String image;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 级别类型：0：系统级别, 1：校园级别, 2：校区级别
     *
     * @see com.gdut.lostfound.common.constant.enums.LevelEnum
     */
    private Integer level;
    /**
     * 作用对象id系统级别为“system”
     */
    private String targetId;

    private Long count;
}

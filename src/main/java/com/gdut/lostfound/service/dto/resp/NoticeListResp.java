package com.gdut.lostfound.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class NoticeListResp {
//    private String icon;
    /**
     * id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 详情
     */
    private String content;
    /**
     * 时间
     */
    private Date time;
    /**
     * 置顶
     *
     * @see com.gdut.lostfound.common.constant.enums.YesNoEnum
     */
    private Integer fixTop;
}

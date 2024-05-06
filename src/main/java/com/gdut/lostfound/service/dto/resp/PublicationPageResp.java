package com.gdut.lostfound.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PublicationPageResp {
    /**
     * 总数据条数
     */
    private Long total;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前页号（0起始）
     */
    private Integer pageNum;

    /**
     * 每页数目
     */
    private Integer pageSize;

    /**
     * 招领列表
     */
    private List<PublicationItem> list;

    // todo 需要有发布时间，以便排序

}

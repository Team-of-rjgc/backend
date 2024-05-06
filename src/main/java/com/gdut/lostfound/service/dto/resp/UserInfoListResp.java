package com.gdut.lostfound.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserInfoListResp {
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
     * 用户信息列表
     */
    private List<UserInfoResp> list;
}

package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.PublicationAddReq;
import com.gdut.lostfound.service.dto.req.PublicationListReq;
import com.gdut.lostfound.service.dto.resp.PublicationDetail;
import com.gdut.lostfound.service.dto.resp.PublicationPageResp;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface LostFoundService {
    /**
     * 发布启事
     */
    void add(PublicationAddReq req, HttpSession session) throws Exception;

    /**
     * 查询列表
     */
    PublicationPageResp page(PublicationListReq req, HttpSession session) throws Exception;

    /**
     * 查看启事详情
     */
    PublicationDetail detail(String id, HttpSession session) throws Exception;

    /**
     * 删除启事（软删除）
     */
    void removeLostFound(List<String> idList, HttpSession session) throws Exception;

    /**
     * 认领物品
     */
    void claim(String id, HttpSession session) throws Exception;
}

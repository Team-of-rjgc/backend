package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.NoticeAddReq;
import com.gdut.lostfound.service.dto.resp.NoticeListResp;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface NoticeService {
    /**
     * 增加通知
     */
    void noticeAdd(NoticeAddReq req, HttpSession session) throws Exception;

    /**
     * 查询通知列表
     */
    List<NoticeListResp> noticeList(HttpSession session) throws Exception;

    /**
     * 删除通知
     */
    void noticeDelete(String id) throws Exception;

    /**
     * 通知置顶切换
     */
    void noticeSwitch(String id) throws Exception;
}

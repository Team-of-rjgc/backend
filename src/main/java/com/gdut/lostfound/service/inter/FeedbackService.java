package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.dao.entity.Feedback;
import com.gdut.lostfound.service.dto.req.FeedbackAddReq;
import com.gdut.lostfound.service.dto.req.FeedbackReplyReq;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * com.gdut.backend.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/22 10:59 Monday
 */
public interface FeedbackService {
    /**
     * 新增反馈
     */
    void addFeedback(FeedbackAddReq req, HttpSession session) throws Exception;

    /**
     * 查看反馈列表
     */
    List<Feedback> listFeedback(HttpSession session) throws Exception;

    /**
     * 回复反馈
     */
    void replyFeedback(FeedbackReplyReq req, HttpSession session) throws Exception;

    /**
     * 反馈标记为已读
     */
    void markFeedback(String id, HttpSession session) throws Exception;

    /**
     * 删除反馈
     */
    void deleteFeedback(String id, HttpSession session) throws Exception;
}

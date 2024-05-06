package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.CommentAddReq;
import com.gdut.lostfound.service.dto.resp.PublicationComment;
import com.gdut.lostfound.service.dto.resp.UserMessage;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommentService {
    /**
     * 查询评论列表
     */
    List<PublicationComment> listComment(String id);

    /**
     * 发布评论
     */
    void commentAdd(CommentAddReq req, HttpSession session) throws Exception;


    /**
     * 用户消息（评论）
     */
    List<UserMessage> listMessage(HttpSession session) throws Exception;

    /**
     * 删除评论
     */
    void removeComment(List<String> idList, HttpSession session) throws Exception;

}

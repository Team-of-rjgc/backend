package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.req.EmailReq;

public interface EmailService {

    /**
     * 发送邮件
     * @param emailReq 邮箱列表
     */
    void send(EmailReq emailReq);
}

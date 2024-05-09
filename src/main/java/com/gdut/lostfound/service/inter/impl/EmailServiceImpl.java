package com.gdut.lostfound.service.inter.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.gdut.lostfound.service.dto.req.EmailReq;
import com.gdut.lostfound.service.inter.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮箱发送接口实现类
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Override
    public void send(EmailReq emailReq) {

        // 读取邮箱配置
        if (host == null || port == null || username == null || password == null) {
            throw new RuntimeException("邮箱配置异常");
        }

        // 设置
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(Integer.parseInt(port));
        // 设置发送人邮箱
        account.setUser(username);
        // 设置发送授权码
        account.setPass(password);
        account.setAuth(true);
        // 设置发送人昵称
        account.setFrom(from);
        // ssl方式发送
        account.setSslEnable(true);
        // 使用安全连接
        account.setStarttlsEnable(true);

        // 发送邮件
        try {
            int size = emailReq.getTos().size();
            Mail.create(account)
                    .setTos(emailReq.getTos().toArray(new String[size]))
                    .setTitle(emailReq.getSubject())
                    .setContent(emailReq.getContent())
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}


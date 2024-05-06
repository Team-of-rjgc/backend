package com.gdut.lostfound.service.utils;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

@Service
public class MailSenderService {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${system.email}")
    private String systemEmail;

    /**
     * 普通文本邮件
     */
    @Async
    public void sendSimple(String receiverEmail, String content, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(systemEmail);
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * HTML内容邮件
     */
    @Async
    public void sendHTMLMessage(String receiverEmail, String htmlString,
                                String subject, String senderEmail, String senderName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress(senderEmail, senderName, "UTF-8"));
        helper.setTo(receiverEmail);
        helper.setSubject(subject);
        helper.setText(htmlString, true);
        message.setSentDate(new Date());
        javaMailSender.send(message);
    }


    /**
     * 使用JetBrick模板引擎渲染HTML邮件内容
     */
    @Async
    public void sendTemplateMessage(String receiverEmail, Map<String, Object> param, String templatePath, String subject,
                                    String senderEmail, String senderName) throws IOException, MessagingException {
        String renderString = render(param, templatePath);
        sendHTMLMessage(receiverEmail, renderString, subject, senderEmail, senderName);
    }

    /**
     * jet-brick模板引擎
     */
    private static final JetEngine engine = JetEngine.create();

    /**
     * 模板渲染
     * https://www.oschina.net/p/jetbrick-template
     */
    private String render(Map<String, Object> param, String templatePath) throws IOException {
        String templateString = getFileContent(templatePath);
        //得到模板文件内容
        JetTemplate template = engine.createTemplate(templateString);
        //设置需要替换的变量值
        StringWriter writer = new StringWriter();
        //模板渲染
        template.render(param, writer);
        return writer.toString();
    }

    /**
     * 获取指定路径下的文件内容
     */
    public String getFileContent(String fileLocation) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileLocation);
        InputStream inputStream = classPathResource.getInputStream();
        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
        return new String(bytes, Charset.forName("UTF-8"));
    }
}

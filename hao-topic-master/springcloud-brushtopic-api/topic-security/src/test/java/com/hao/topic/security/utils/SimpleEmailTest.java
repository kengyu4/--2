package com.hao.topic.security.utils;

import com.hao.topic.security.constant.EmailConstant;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 简单邮件发送测试类
 */
public class SimpleEmailTest {

    public static void main(String[] args) {
        // 创建JavaMailSender实例
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // 设置邮件服务器配置
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setUsername("2769565379@qq.com");
        mailSender.setPassword("vrlrtdjjponedgdc");
        
        // 设置邮件属性
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        
        try {
            // 创建邮件消息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("2769565379@qq.com");
            message.setTo("2769565379@qq.com");
            message.setSubject(EmailConstant.EMAIL_TITLE.getValue());
            message.setText(EmailConstant.EMAIL_MESSAGE.getValue() + "TEST123" + EmailConstant.EMAIL_OUT_TIME.getValue());
            
            // 发送邮件
            mailSender.send(message);
            System.out.println("邮件发送成功！");
        } catch (Exception e) {
            System.err.println("邮件发送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

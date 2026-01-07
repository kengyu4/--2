package com.hao.topic.security.utils;

import com.hao.topic.security.constant.EmailConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;



/**
 * Description: 发送邮箱验证码工具类
 * Author: Hao
 * Date: 2025/5/2 18:21
 */
@Slf4j
@Service
public class EmailSendUtils {
    // 注入JavaMailSender接口
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    // 通过value注解得到配置文件中发送者的邮箱
    @Value("${spring.mail.username}")
    String userName;// 用户发送者



    /**
     * 创建一个发送邮箱验证的方法
     *
     * @param to
     */
    public void sendVerificationEmail(String to) {
        StringBuilder emailCode = new StringBuilder();
        try {
            // 定义email信息格式
            SimpleMailMessage message = new SimpleMailMessage();
            // 调用生成6位数字和字母的方法，生成验证码
            String code = generateRandomCode(to, emailCode);
            // 设置发件人
            message.setFrom(userName);
            // 接收者邮箱，为调用本方法传入的接收者的邮箱xxx@qq.com
            message.setTo(to);
            // 邮件主题
            message.setSubject(EmailConstant.EMAIL_TITLE.getValue());
            // 邮件内容  设置的邮件内容，这里我使用了常量类字符串，加上验证码，再加上常量类字符串
            message.setText(EmailConstant.EMAIL_MESSAGE.getValue() + code + EmailConstant.EMAIL_OUT_TIME.getValue());
            // 开始发送
            mailSender.send(message);
        } catch (Exception e) {
            // 捕获任何发送过程中的异常
            log.error("【邮件工具类】发送验证码邮件失败，收件人：{}， 错误原因：", to, e);
            // 必须将异常重新抛出，通知调用方发送失败
            throw e;
        }
    }


    /**
     * 随机生成6位字母加数字组合的验证码
     *
     * @return
     */
    public String generateRandomCode(String email, StringBuilder emailCode) {
        // 字母和数字组合
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // 拆分每一个字符放到数组中
        char[] newStr = str.toCharArray();
        // 循环随机生成6为数字和字母组合
        for (int i = 0; i < 6; i++) {
            // 通过循环6次，为stringBuilder追加内容，内容为随机数✖62，取整
            emailCode.append(newStr[(int) (Math.random() * 62)]);
        }
        String code = emailCode.toString();
        // 存入Redis中并设置时长为5分钟
        String redisKey = EmailConstant.EMAIL_CODE.getValue() + email;
        try {
            redisTemplate.opsForValue().set(redisKey, code, 5, TimeUnit.MINUTES);
            // 立即验证是否写入成功
            Object savedCode = redisTemplate.opsForValue().get(redisKey);
            if (savedCode != null) {
                log.info("验证码已成功存入Redis - Key: {}, Code: {}, 验证读取: {}", redisKey, code, savedCode);
            } else {
                log.error("验证码存入Redis失败！Key: {}, Code: {}", redisKey, code);
            }
        } catch (Exception e) {
            log.error("Redis操作异常 - Key: {}, Error: {}", redisKey, e.getMessage(), e);
        }
        return code;
    }
}
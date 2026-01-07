package com.hao.topic.security.utils;

import com.hao.topic.security.TopicSecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 邮件发送集成测试
 */
@SpringBootTest(classes = TopicSecurityApplication.class)
@ActiveProfiles("dev")
public class EmailSendUtilsIntegrationTest {

    @Autowired
    private EmailSendUtils emailSendUtils;

    @Test
    public void testSendVerificationEmailIntegration() {
        // 测试发送邮件到指定邮箱
        emailSendUtils.sendVerificationEmail("2769565379@qq.com");
        System.out.println("邮件发送测试已完成，请检查邮箱是否收到验证码");
    }
}

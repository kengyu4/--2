package com.hao.topic.security.utils;

import com.hao.topic.security.constant.EmailConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSendUtilsTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private EmailSendUtils emailSendUtils;

    @Test
    public void testGenerateRandomCode() {
        // 模拟Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        
        StringBuilder emailCode = new StringBuilder();
        String code = emailSendUtils.generateRandomCode("test@example.com", emailCode);
        
        assertEquals(6, code.length());
        assertTrue(code.matches("[a-zA-Z0-9]{6}"));
        assertEquals(code, emailCode.toString());
    }

    @Test
    public void testSendVerificationEmail() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        
        emailSendUtils.userName = "sender@example.com";
        
        emailSendUtils.sendVerificationEmail("recipient@example.com");
        
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
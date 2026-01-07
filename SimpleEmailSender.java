import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class SimpleEmailSender {
    public static void main(String[] args) {
        // 创建邮件发送器
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // 配置邮件服务器
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setUsername("2769565379@qq.com");
        mailSender.setPassword("vrlrtdjjponedgdc");
        
        // 设置邮件属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(props);
        
        // 创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2769565379@qq.com");
        message.setTo("2769565379@qq.com");
        message.setSubject("【刷题社区】邮箱验证");
        message.setText("您好！您的邮箱验证码是：TEST123 ，该验证码5分钟内有效。请尽快完成验证！");
        
        try {
            // 发送邮件
            mailSender.send(message);
            System.out.println("邮件发送成功！");
        } catch (Exception e) {
            System.out.println("邮件发送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

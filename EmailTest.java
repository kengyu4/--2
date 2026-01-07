import java.util.Random;

public class EmailTest {
    public static void main(String[] args) {
        // 测试验证码生成逻辑
        String code = generateRandomCode();
        System.out.println("生成的验证码: " + code);
        
        // 模拟邮件发送逻辑
        String toEmail = "2769565379@qq.com";
        System.out.println("准备发送邮件到: " + toEmail);
        System.out.println("邮件主题: 【刷题社区】邮箱验证");
        System.out.println("邮件内容: 您好！您的邮箱验证码是：" + code + " ，该验证码5分钟内有效。请尽快完成验证！");
        System.out.println("邮件发送成功！");
    }
    
    public static String generateRandomCode() {
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] newStr = str.toCharArray();
        StringBuilder emailCode = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 6; i++) {
            emailCode.append(newStr[random.nextInt(newStr.length)]);
        }
        
        return emailCode.toString();
    }
}

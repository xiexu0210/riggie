package com.xiexu.reggie;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class abcTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public  void cout(){

        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送人
        message.setFrom("2638545848@qq.com");
        // 邮件接收人
        message.setTo("2638545848@qq.com");
        // 邮件标题啊
        message.setSubject("通知：开会");
        // 邮件内容
        message.setText("1点开会");

        mailSender.send(message);

    }
}

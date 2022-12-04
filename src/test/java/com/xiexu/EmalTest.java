//package com.xiexu;
//
//
//import java.security.GeneralSecurityException;
//import java.util.Properties;
//import java.util.Random;
//
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//
//
//import com.sun.mail.util.MailSSLSocketFactory;
//import com.xiexu.reggie.ReggieApplication;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//@SpringBootTest(classes = {com.xiexu.reggie.ReggieApplication.class})
//public class EmalTest {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Test
//    public void test1() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        // 邮件发送人
//        message.setFrom("2638545848@qq.com");
//        // 邮件接收人
//        message.setTo("2638545848@qq.com");
//        // 邮件标题啊
//        message.setSubject("通知：开会");
//        // 邮件内容
//        message.setText("1点开会");
//        mailSender.send(message);
//    }
//
//
//
//}
//
//
//

package com.example.demo.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class EmailService {
    JavaMailSenderImpl mailSender;
    public EmailService() {
        try {
            mailSender = new JavaMailSenderImpl();
            Properties userProps = getConfProps("user.properties");
            Properties mailProps = getConfProps("email.properties");
            Properties javaMailProps = mailSender.getJavaMailProperties();
            javaMailProps.putAll(mailProps);
            System.out.println(javaMailProps.get("mail.smtp.starttls.enable"));
            mailSender.setJavaMailProperties(javaMailProps);
            mailSender.setHost(userProps.getProperty("email.host"));
            mailSender.setUsername(userProps.getProperty("email.user"));
            mailSender.setPassword(userProps.getProperty("email.password"));
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void sendMail(String target, String title, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(target);
        msg.setSubject(title);
        msg.setText(body);

        mailSender.send(msg);
    }

    private Properties getConfProps(String confName) {
        Properties properties = null;
        try {
            Resource resource = new ClassPathResource("conf");
            URI uri = resource.getURI();
            String targetProp = Paths.get(uri).resolve(confName).toString();
            FileInputStream fis = new FileInputStream(targetProp);
            properties = new Properties();
            properties.load(fis);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(properties != null) {
            return properties;
        } else {
            throw new RuntimeException("properties is null");
        }
    }
}

package com.brightkut.userservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.brightkut.commonlib.lib.exception.InternalServerException;
import com.brightkut.userservice.config.EmailServiceConfig;
import com.brightkut.userservice.dto.DetailEmailDto;

@Service
public class EmailService {

    private final String sender;
    private final JavaMailSender javaMailSender;

    public EmailService(EmailServiceConfig emailServiceConfig, JavaMailSender javaMailSender) {
        this.sender = emailServiceConfig.getUsername();
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail(DetailEmailDto details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getEmailBody());
            mailMessage.setSubject(details.getEmailSubject());

            javaMailSender.send(mailMessage);
        }

        catch (Exception exception) {
            throw new InternalServerException("Error whene sending email");
        }
    }
}

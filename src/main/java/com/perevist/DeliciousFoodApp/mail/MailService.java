package com.perevist.DeliciousFoodApp.mail;

import com.perevist.DeliciousFoodApp.exception.DeliciousFoodAppException;
import com.perevist.DeliciousFoodApp.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${email.address}")
    private String emailAddress;

    @Async
    public void sendMail(String subject, String email, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailAddress);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content);
        };

        try{
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e.getMessage());
            throw new DeliciousFoodAppException(Error.MAIL_CAN_NOT_BE_SENT);
        }
    }
}

package com.sandoval.notification.cms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender mailSender;

  @Value("${notification.mail.from}")
  private String fromEmail;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }


  public void sendEmail(String to, String subject, String body) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);

      mailSender.send(message);
      LOGGER.info("Email sent to: {}", to);
    } catch (Exception e) {
      LOGGER.error("Failed to send email to: {}", to, e);
    }
  }
}
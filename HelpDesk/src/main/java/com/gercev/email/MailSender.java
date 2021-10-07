package com.gercev.email;

import com.gercev.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    public void sendAcceptableEmail(List<User> recipients, Long id, EmailTemplate emailTemplate) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        Context context = new Context();
        context.setVariable("id", id);

        ExecutorService emailExecutorService = Executors.newSingleThreadExecutor();
        emailExecutorService.execute(() -> {
            recipients.forEach(user -> {
                try {
                    context.setVariable("user", user);
                    helper.setSubject(emailTemplate.getSubject());
                    helper.setFrom("hertsauhduser1@gmail.com");
                    helper.setTo("vladislavgercevx@gmail.com");
                    String htmlContext = this.htmlTemplateEngine.process(emailTemplate.getTemplateName(), context);
                    helper.setText(htmlContext, true);
                    this.mailSender.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}

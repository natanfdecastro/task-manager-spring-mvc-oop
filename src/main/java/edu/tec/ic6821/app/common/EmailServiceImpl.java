package edu.tec.ic6821.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSampleEmail(String to, String name) {
        Context context = new Context();
        context.setVariable("name", name);

        String message = templateEngine.process("sampleMail.html", context);

        send("noaddress@nomail.com", to, "Sample Email", message);
    }

    private void send(String from, String to, String subject, String text) {
        MimeMessagePreparator messagePreparator = (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
        };

        javaMailSender.send(messagePreparator);
    }

}

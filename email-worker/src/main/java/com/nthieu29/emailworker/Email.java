package com.nthieu29.emailworker;

import lombok.Data;
import org.springframework.mail.SimpleMailMessage;

import javax.validation.constraints.NotBlank;

@Data
public class Email {
    @NotBlank(message = "recipient is required")
    private String recipient;

    @NotBlank(message = "subject is required")
    private String subject;

    @NotBlank(message = "content is required")
    private String content;

    public SimpleMailMessage toSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        return simpleMailMessage;
    }
}

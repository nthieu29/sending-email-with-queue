package com.nthieu29.emailservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(EmailController.EMAIL_PATH)
@Slf4j
public class EmailController {
    public static final String EMAIL_PATH = "emails";
    private final Source source;

    public EmailController(Source source) {
        this.source = source;
    }

    @PostMapping
    public Email addProduct(@Valid @RequestBody Email email) {
        Message<Email> messageEmail = MessageBuilder.withPayload(email).build();
        source.output().send(messageEmail);
        log.info("Sent Email: " + email);
        return email;
    }
}

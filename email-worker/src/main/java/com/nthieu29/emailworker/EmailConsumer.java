package com.nthieu29.emailworker;

import com.azure.spring.integration.core.api.Checkpointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static com.azure.spring.integration.core.AzureHeaders.CHECKPOINTER;

@Component
@Slf4j
public class EmailConsumer {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Bean
    public Consumer<Message<Email>> consume() {
        return emailMessage -> {
            Checkpointer checkpointer = (Checkpointer) emailMessage.getHeaders().get(CHECKPOINTER);
            Email email = emailMessage.getPayload();
            log.info("Get the email: " + email);
            javaMailSender.send(email.toSimpleMailMessage());
            checkpointer.success().handle((r, ex) -> {
                if (ex == null) {
                    log.info("Email '{}' successfully checkpointed", email);
                }
                return null;
            });
        };
    }
}

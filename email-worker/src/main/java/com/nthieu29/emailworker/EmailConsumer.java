package com.nthieu29.emailworker;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

@EnableBinding(Sink.class)
@Slf4j
public class EmailConsumer {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @StreamListener(Sink.INPUT)
    public void processEmail(Email email,
                             @Header(AmqpHeaders.CHANNEL) Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
        log.info("Get the email: " + email);
        javaMailSender.send(email.toSimpleMailMessage());
        channel.basicAck(deliveryTag, false);
    }
}

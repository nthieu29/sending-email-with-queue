package com.nthieu29.emailservice;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Email {
    @NotBlank(message = "recipient is required")
    private String recipient;

    @NotBlank(message = "subject is required")
    private String subject;

    @NotBlank(message = "content is required")
    private String content;
}

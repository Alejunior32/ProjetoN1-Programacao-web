package com.uam.projetoN1.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class EmailSenderServiceTest {

    public static final String EMAIL = "gft@gft.com";
    public static final String SUBJECT = "esse é o subject";
    public static final String BODY = "esse é o body";
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    private String toEmail;
    private String subject;
    private String body;

    private SimpleMailMessage message;

    @BeforeEach
    public void setUp(){
        openMocks(this);
        start();
    }

    @Test
    public void deveChamarEnviarEmailEVerificarSeFoiChamado(){
        Mockito.doNothing().when(mailSender).send((SimpleMailMessage) any());
        emailSenderService.EnviarEmail(toEmail,subject,body);
        verify(mailSender,times(1)).send((SimpleMailMessage) any());
    }

    private void start(){
        toEmail = EMAIL;
        subject = SUBJECT;
        body = BODY;
    }

}

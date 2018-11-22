package com.rapp.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.NotImplementedException;
import com.rapp.email.exception.SendEmailException;
import com.rapp.email.plugin.MailPluginFactory;

/**
 * Email Service implementation
 * 
 * @author Rasanka Jayawardana
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

    private MailPluginFactory pluginFactory;

    @Autowired
    public EmailServiceImpl(final MailPluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    @Override
    public void sendEmail(EmailDto emailDto) throws SendEmailException, NotImplementedException {
        pluginFactory.createInstance().sendMail(emailDto);
    }

}

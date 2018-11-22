package com.rapp.email.plugin;

import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.SendEmailException;

public interface MailPlugin {

    public void sendMail(EmailDto emailDto) throws SendEmailException; 
}

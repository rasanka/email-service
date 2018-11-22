package com.rapp.email.service;

import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.NotImplementedException;
import com.rapp.email.exception.SendEmailException;

/**
 * Email service interface
 * 
 * @author Rasanka Jayawardana
 *
 */
public interface EmailService {

    /**
     * This method will send and email to the specified recipients
     * 
     * @param emailDto
     * @throws SendEmailException
     */
    void sendEmail(EmailDto emailDto) throws SendEmailException, NotImplementedException;
}

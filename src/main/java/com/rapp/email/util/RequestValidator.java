package com.rapp.email.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.BadRequestException;
import com.rapp.email.service.EmailServiceImpl;

/**
 * Request Validator
 * 
 * @author Rasanka Jayawardana
 *
 */
public class RequestValidator {

    private static final String INVALID_EMAIL_SUBJECT = "Invalid Email Subject";
    private static final String INVALID_EMAIL_CONTENT = "Invalid Email Content";
    private static final String INVALID_EMAIL_ADDRESS = "Invalid Email Address";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    /**
     * Validate email request
     * 
     * @param emailDto
     * @throws BadRequestException
     */
    public static void validateEmailRequest(EmailDto emailDto) throws BadRequestException {

        checkRequiredEmailAddresses(emailDto.getToAddresses());

        if (StringUtils.isBlank(emailDto.getEmailBody())) {
            LOGGER.error("Empty email body - {}", emailDto.getToAddresses());
            throw new BadRequestException(INVALID_EMAIL_CONTENT);
        }

        if (StringUtils.isBlank(emailDto.getEmailSubject())) {
            LOGGER.error("Empty email subject - {}", emailDto.getToAddresses());
            throw new BadRequestException(INVALID_EMAIL_SUBJECT);
        }

        checkEmailAddresses(emailDto.getCcAddresses());
        checkEmailAddresses(emailDto.getBccAddresses());
    }

    private static void checkRequiredEmailAddresses(String emailList) throws BadRequestException {
        if (StringUtils.isBlank(emailList)) {
            throw new BadRequestException(INVALID_EMAIL_ADDRESS);
        } else {
            checkEmailAddresses(emailList);
        }
    }

    private static void checkEmailAddresses(String emailList) throws BadRequestException {
        if (StringUtils.isNotBlank(emailList)) {
            List<String> emails = Stream.of(emailList.split(",")).collect(Collectors.toList());
            for (String email : emails) {
                if (!InputValidator.isValidEmailAddress(email)) {
                    throw new BadRequestException(INVALID_EMAIL_ADDRESS);
                }
            }
        }
    }
}

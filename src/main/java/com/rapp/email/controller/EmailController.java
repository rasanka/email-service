package com.rapp.email.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rapp.email.constant.ApplicationConstants;
import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.BadRequestException;
import com.rapp.email.exception.NotImplementedException;
import com.rapp.email.exception.SendEmailException;
import com.rapp.email.service.EmailService;
import com.rapp.email.service.QuoteService;
import com.rapp.email.util.RequestValidator;

/**
 * Email Service API email controller
 * 
 * @author Rasanka Jayawardana
 *
 */
@RestController
public class EmailController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private QuoteService quoteService;


    /**
     * This end point will send and email to all the recipients specified in the request body
     * Sample input can be found in the API spec swagger document
     * 
     * @param request
     * @param correlationId
     * @param emailRequest
     * @param enrich
     * @return
     */
    // @formatter:off
    @RequestMapping(path = ApplicationConstants.SEND_EMAIL_ENDPOINT, 
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmail(HttpServletRequest request, 
            @RequestParam(value = ApplicationConstants.REQUEST_PARAMETER_ENRICH, required = false) boolean enrich,
            @RequestBody(required = true) EmailDto emailRequest) throws BadRequestException, SendEmailException, NotImplementedException {
        // @formatter:on       

        LOGGER.debug("EmailController - sendEmail() Started! - {}", MDC.get(ApplicationConstants.HEADER_CORRELATION_ID));
        RequestValidator.validateEmailRequest(emailRequest);

        if (enrich) {
            appendRandomQuote(emailRequest);
        }

        emailService.sendEmail(emailRequest);

        LOGGER.debug("EmailController - sendEmail() Ended!");
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    private void appendRandomQuote(EmailDto emailRequest) {
        String quoteOfTheDay = quoteService.getQuote();
        if (StringUtils.isNotBlank(quoteOfTheDay)) {
            StringBuilder builder = new StringBuilder(emailRequest.getEmailBody());
            builder.append(ApplicationConstants.HTML_LINE_BREAK).append(ApplicationConstants.HTML_LINE_BREAK).append(
                ApplicationConstants.QUOTE_HEADING).append(ApplicationConstants.HTML_LINE_BREAK).append(quoteOfTheDay);
            emailRequest.setEmailBody(builder.toString());
        }
    }
}

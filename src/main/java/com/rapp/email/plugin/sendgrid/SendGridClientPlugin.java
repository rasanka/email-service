package com.rapp.email.plugin.sendgrid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapp.email.config.EmailConfiguration;
import com.rapp.email.constant.ApplicationConstants;
import com.rapp.email.dto.AddressListDto;
import com.rapp.email.dto.EmailDto;
import com.rapp.email.exception.SendEmailException;
import com.rapp.email.plugin.MailPlugin;
import com.rapp.email.security.util.EncryptionUtil;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendGridClientPlugin implements MailPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridClientPlugin.class);

    private EmailConfiguration emailConfiguration;

    public SendGridClientPlugin(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    @Override
    public void sendMail(EmailDto emailDto) throws SendEmailException {

        LOGGER.debug("sendEmail() - Sending email to {} ", emailDto.getToAddresses());

        AddressListDto addressListDto = filterEmails(emailDto);
        if (addressListDto.getToList().isEmpty()) {
            LOGGER.warn("All the TO email addresses filtered!");
        } else {
            Mail mail = buildEmail(emailDto, addressListDto);
            SendGrid sendGrid = new SendGrid(decryptApiKey());
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint(ApplicationConstants.SENDGRID_API_ENDPOINT);
            Response response;
            try {
                request.setBody(mail.build());
                response = sendGrid.api(request);
            } catch (IOException e) {
                String errMsg = "Error calling the SendGrid API to send mail : IOException message = " + e.getMessage();
                throw new SendEmailException(errMsg);
            }

            if (response.getStatusCode() != HttpStatus.SC_ACCEPTED) {
                LOGGER.warn("Send grid response status code is not 202, statusCode=" + response.getStatusCode());
            }
        }
    }
    
    private String decryptApiKey() throws SendEmailException {
        try {
            return EncryptionUtil.decrypt(emailConfiguration.getApiKey(), emailConfiguration.getEncryptionSecret());
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            LOGGER.error("Error occured while decrypting the api key - {}", e.getMessage());
            throw new SendEmailException("Decryption Error!");
        }
    }

    private AddressListDto filterEmails(EmailDto emailDto) {
        AddressListDto addressListDto = new AddressListDto();
        if (emailConfiguration.getFilterEmailsEnabled()) {

            List<String> toList = getEmailList(emailDto.getToAddresses());
            List<String> ccList = getEmailList(emailDto.getCcAddresses());
            List<String> bccList = getEmailList(emailDto.getBccAddresses());

            LOGGER.info("Email Filter Enabled");
            List<String> allMails = Stream.of(toList, ccList, bccList).flatMap(x -> x.stream()).collect(Collectors.toList());
            logFilteredList(allMails);

            addressListDto.setToList(filterEmails(toList));
            addressListDto.setCcList(filterEmails(ccList));
            addressListDto.setBccList(filterEmails(bccList));

        } else {
            addressListDto.setToList(getEmailList(emailDto.getToAddresses()));
            addressListDto.setCcList(getEmailList(emailDto.getCcAddresses()));
            addressListDto.setBccList(getEmailList(emailDto.getBccAddresses()));

        }
        return addressListDto;
    }

    private List<String> getEmailList(String emails) {
        return (StringUtils.isNotBlank(emails)) ? Stream.of(emails.split(",")).collect(Collectors.toList()) : Collections.emptyList();
    }

    private List<String> filterEmails(List<String> emailList) {
        return emailList.stream().filter(a -> a.contains(emailConfiguration.getFilterDomain())).collect(Collectors.toList());
    }

    private void logFilteredList(List<String> filteredList) {
        LOGGER.info("---------------------------------------------");
        filteredList.stream().filter(a -> !a.contains(emailConfiguration.getFilterDomain())).collect(Collectors.toList()).forEach(
            a -> LOGGER.info(a));
        LOGGER.info("---------------------------------------------");
    }

    private Mail buildEmail(EmailDto emailDto, AddressListDto addressListDto) {

        LOGGER.debug("SendGridClient - buildEmail() - Building Email!");
        Mail mail = new Mail();
        Personalization personalization = new Personalization();

        Email from = new Email(emailConfiguration.getFromAddress());
        mail.setFrom(from);
        mail.setSubject(emailDto.getEmailSubject());

        addressListDto.getToList().forEach(email -> personalization.addTo(new Email(email)));
        addressListDto.getCcList().forEach(email -> personalization.addCc(new Email(email)));
        addressListDto.getBccList().forEach(email -> personalization.addBcc(new Email(email)));

        mail.addPersonalization(personalization);
        Content content = new Content(ApplicationConstants.SENDGRID_EMAIL_CONTENT_TYPE, emailDto.getEmailBody());
        mail.addContent(content);

        return mail;
    }

}

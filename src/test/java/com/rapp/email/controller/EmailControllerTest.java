package com.rapp.email.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rapp.email.constant.ApplicationConstants;
import com.rapp.email.dto.EmailDto;
import com.rapp.email.service.EmailService;
import com.rapp.email.service.QuoteService;
import com.rapp.email.util.JsonUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailService emailService;

    @MockBean
    private QuoteService quoteService;

    private EmailDto mockEmail() {
        EmailDto email = new EmailDto();
        email.setToAddresses("a@aa.com");
        email.setCcAddresses("b@bb.com");
        email.setBccAddresses("c@cc.com");
        email.setEmailBody("Test Content");
        email.setEmailSubject("Test Subject");
        return email;
    }

    @Test
    public void testSendEmailSuccess() throws Exception {

        EmailDto emailRequest = mockEmail();
        String json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isOk());

    }
    
    @Test
    public void testSendEmailSuccess_requiredFields() throws Exception {

        EmailDto email = new EmailDto();
        email.setToAddresses("a@aa.com");
        email.setEmailBody("Test Content");
        email.setEmailSubject("Test Subject");
        String json = JsonUtil.toJson(email);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isOk());

    }
    
    @Test
    public void testSendEmailSuccess_multipleEmailAddresses() throws Exception {

        EmailDto email = mockEmail();
        email.setToAddresses("a@aa.com,b@bb.com,c@cc.com");
        String json = JsonUtil.toJson(email);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isOk());

    }
    
    @Test
    public void testSendEmailInvalid_multipleEmailAddresses() throws Exception {

        EmailDto email = mockEmail();
        email.setToAddresses("a@aa.com,b@,c@cc.com");
        String json = JsonUtil.toJson(email);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());

    }    
    
    @Test
    public void testSendEmailInvalidRequest() throws Exception {

        EmailDto emailRequest = mockEmail();
        emailRequest.setToAddresses("aa.com");
        String json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());
        
        emailRequest = mockEmail();
        emailRequest.setEmailSubject(null);
        json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());
        
        emailRequest = mockEmail();
        emailRequest.setEmailBody(null);
        json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());
        
        emailRequest = mockEmail();
        emailRequest.setCcAddresses("aa");
        json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());
        
        emailRequest = mockEmail();
        emailRequest.setBccAddresses("aa");
        json = JsonUtil.toJson(emailRequest);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isBadRequest());

    }
    
    @Test
    public void testSendEmailSuccess_enrichTrue() throws Exception {

        EmailDto email = mockEmail();
        String json = JsonUtil.toJson(email);

        mvc.perform(post(ApplicationConstants.BASE_URL + ApplicationConstants.SEND_EMAIL_ENDPOINT+"?enrich=true")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)).andExpect(status().isOk());

    }
}

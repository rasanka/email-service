package com.rapp.email.quote;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.rapp.email.config.EmailConfiguration;
import com.rapp.email.dto.QuoteDto;

@Component
public class QuoteClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailConfiguration emailConfig;

    /**
     * This function will call the external end point and map the response in to Quote DTO
     * 
     * @return quoteDto
     */
    public QuoteDto getQuote() {
        LOGGER.debug("QuoteClient - getQuote() - Calling external endpoint to get a Quote");
        ResponseEntity<QuoteDto> quote = null;
        try {
            RequestEntity<?> accountsRequest = RequestEntity.get(URI.create(emailConfig.getQuoteApiUrl())).build();
            quote = restTemplate.exchange(accountsRequest, QuoteDto.class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error occured while calling the Quote endpoint");
        }

        return quote != null && quote.getBody() != null ? quote.getBody() : null;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}

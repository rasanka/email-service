package com.rapp.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rapp.email.dto.QuoteDto;
import com.rapp.email.quote.QuoteClient;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private QuoteClient quoteClient;

    @Override
    public String getQuote() {
        QuoteDto quote = quoteClient.getQuote();
        return quote.getQuote();
    }

}

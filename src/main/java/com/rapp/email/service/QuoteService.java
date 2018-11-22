package com.rapp.email.service;

/**
 * Interface related to Quote of the day
 * 
 * @author Rasanka Jayawardana
 *
 */
public interface QuoteService {

    /**
     * This method will return a random quote retrieved from external end point
     * 
     * @return random quote
     */
    String getQuote();
}

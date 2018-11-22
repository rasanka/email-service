package com.rapp.email.constant;

/**
 * Constants used in the application
 * 
 * @author Rasanka Jayawardana
 *
 */
public class ApplicationConstants {

    public static final String BASE_URL = "/api/v1";
    public static final String SEND_EMAIL_ENDPOINT = "/email";
    public static final String REQUEST_PARAMETER_ENRICH = "enrich";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-ID";

    public static final String SENDGRID_API_ENDPOINT = "mail/send";
    public static final String SENDGRID_EMAIL_CONTENT_TYPE = "text/html";
    public static final String HTML_LINE_BREAK = "<br/>";
    public static final String QUOTE_HEADING = "Quote Of the Day";
}

package com.rapp.email.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.rapp.email.exception.ConfigurationException;

/**
 * Application configuration mapper
 * 
 * @author Rasanka Jayawardana
 *
 */
@Configuration
@ConfigurationProperties(prefix = "configurations")
public class EmailConfiguration {

    private String emailClient;
    private String encryptionSecret;
    private String apiKey;
    private String fromAddress;
    private Boolean filterEmailsEnabled;
    private String filterDomain;
    private String quoteApiUrl;

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getEncryptionSecret() {
        return encryptionSecret;
    }

    public void setEncryptionSecret(String encryptionSecret) {
        this.encryptionSecret = encryptionSecret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Boolean getFilterEmailsEnabled() {
        return filterEmailsEnabled;
    }

    public void setFilterEmailsEnabled(Boolean filterEmailsEnabled) {
        this.filterEmailsEnabled = filterEmailsEnabled;
    }

    public String getFilterDomain() {
        return filterDomain;
    }

    public void setFilterDomain(String filterDomain) {
        this.filterDomain = filterDomain;
    }

    public String getQuoteApiUrl() {
        return quoteApiUrl;
    }

    public void setQuoteApiUrl(String quoteApiUrl) {
        this.quoteApiUrl = quoteApiUrl;
    }

    public void validate() throws ConfigurationException {

        if (StringUtils.isBlank(emailClient)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'emailClient' is not present");
        }
        if (StringUtils.isBlank(encryptionSecret)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'encryptionSecret' is not present");
        }
        if (StringUtils.isBlank(apiKey)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'apiKey' is not present");
        }
        if (StringUtils.isBlank(fromAddress)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'fromAddress' is not present");
        }
        if (StringUtils.isBlank(filterDomain)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'filterDomain' is not present");
        }
        if (StringUtils.isBlank(quoteApiUrl)) {
            throw new ConfigurationException("Missing Configuration: EmailConfiguration 'quoteApiUrl' is not present");
        }
    }

}

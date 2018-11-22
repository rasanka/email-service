package com.rapp.email.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailDto {

    @JsonProperty(value = "to")
    private String toAddresses;

    @JsonProperty(value = "cc")
    private String ccAddresses;

    @JsonProperty(value = "bcc")
    private String bccAddresses;

    @JsonProperty(value = "body")
    private String emailBody;

    @JsonProperty(value = "subject")
    private String emailSubject;

    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(String ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public String getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(String bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

}

package com.rapp.email.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rapp.email.config.EmailConfiguration;
import com.rapp.email.exception.NotImplementedException;
import com.rapp.email.plugin.sendgrid.SendGridClientPlugin;

@Component
public class MailPluginFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailPluginFactory.class);
    private static final String SENDGRID_PLUGIN = "sendgrid";

    @Autowired
    private EmailConfiguration emailConfig;

    private MailPlugin mailPlugin;

    public MailPlugin createInstance() throws NotImplementedException {
        if (emailConfig.getEmailClient().equals(SENDGRID_PLUGIN)) {
            mailPlugin = new SendGridClientPlugin(emailConfig);
        } else {
            LOGGER.error("{} Plugin Not implemented!", emailConfig.getEmailClient());
            throw new NotImplementedException("Plugin Not implemented for the client" + emailConfig.getEmailClient());
        }
        return mailPlugin;
    }
}

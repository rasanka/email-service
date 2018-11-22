package com.rapp.email.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rapp.email.config.EmailConfiguration;
import com.rapp.email.exception.NotImplementedException;
import com.rapp.email.plugin.sendgrid.SendGridClientPlugin;

@RunWith(MockitoJUnitRunner.class)
public class MailPluginFactoryTest {

    @InjectMocks
    MailPluginFactory factory;

    @Mock
    private EmailConfiguration emailConfig;

    @Test
    public void testSendGridPlugin() throws NotImplementedException {

        when(emailConfig.getEmailClient()).thenReturn("sendgrid");
        MailPlugin mailPlugin = factory.createInstance();

        assertNotNull("An integration plugin should be created for SendGrid", mailPlugin);

        assertEquals(SendGridClientPlugin.class, mailPlugin.getClass());
    }

    @Test(expected = NotImplementedException.class)
    public void testNotImplementedException() throws NotImplementedException {

        when(emailConfig.getEmailClient()).thenReturn("mailchimp");
        when(factory.createInstance()).thenThrow(new NotImplementedException());

    }
}

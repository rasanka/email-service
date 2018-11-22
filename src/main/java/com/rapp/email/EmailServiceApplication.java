package com.rapp.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rapp.email.config.EmailConfiguration;

/**
 * Email Service Application
 * 
 * @author Rasanka Jayawardana
 *
 */
@SpringBootApplication
public class EmailServiceApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailServiceApplication.class);

    public static void main(String[] args) {

        try {
            LOGGER.info(":: Starting Email Service Application ::");

            ApplicationContext ctx = SpringApplication.run(EmailServiceApplication.class, args);

            EmailConfiguration emailConfiguration = ctx.getBean(EmailConfiguration.class);
            emailConfiguration.validate();
        } catch (Exception ex) {
            LOGGER.error("Error initiating application: " + ex.getMessage(), ex);
            System.exit(1);
        }

    }
}

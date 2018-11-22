package com.rapp.email.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Input Validator
 * 
 * @author Rasanka
 *
 */
public class InputValidator {
    private static final String VALID_EMAIL_REGULAR_EXPR = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+$";

    public static boolean isValidEmailAddress(String emailAddress) {
        return StringUtils.isNotEmpty(emailAddress) && emailAddress.matches(VALID_EMAIL_REGULAR_EXPR);
    }

}

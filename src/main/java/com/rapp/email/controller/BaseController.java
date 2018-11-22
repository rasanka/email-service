package com.rapp.email.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rapp.email.constant.ApplicationConstants;

/**
 * Email Service API base controller
 * 
 * @author Rasanka Jayawardana
 *
 */
@RestController
@RequestMapping(value = ApplicationConstants.BASE_URL)
public abstract class BaseController {

}

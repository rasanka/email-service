package com.rapp.email.controller.exception;

import com.rapp.email.dto.ErrorResponseDto;
import com.rapp.email.exception.BadRequestException;
import com.rapp.email.exception.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Generic Controller Advice to handle exceptions thrown in the controller
 * 
 * @author vagrant
 *
 */
@ControllerAdvice
public class GenericControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericControllerAdvice.class);
    public static final String EXCEPTION_MSG_FOR_API_CALL = "Error occured during the API call - {}";

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        LOGGER.error(EXCEPTION_MSG_FOR_API_CALL, e.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = NotImplementedException.class)
    public ResponseEntity<?> notImplemented(NotImplementedException e) {
        LOGGER.error(EXCEPTION_MSG_FOR_API_CALL, e.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        LOGGER.error(EXCEPTION_MSG_FOR_API_CALL, e.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

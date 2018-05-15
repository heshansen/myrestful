package com.topbaby.Security;

import com.topbaby.exceptions.AccessDeniedException;
import com.topbaby.common.ErrorMessage;
import com.topbaby.exceptions.AccountNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Created by Qing on 05/01/2017.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handlerAccessDeniedException(Exception ex,  WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorMessage errorMessage = new ErrorMessage(status.value(), ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, headers, status);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<Object> handlerAccountNotFoundException(Exception ex,  WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorMessage errorMessage = new ErrorMessage(status.value(), ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, headers, status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerRequestExpiredException(Exception ex,  WebRequest request){
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorMessage errorMessage = new ErrorMessage(status.value(), ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(status.value(), ex.getMessage());
        return new ResponseEntity<Object>(errorMessage, headers, status);
    }
}

package com.parcom.classroom.exceptions;

import com.parcom.i18n.LocalizationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static java.util.Optional.ofNullable;


@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    private final MessageSource messageSource;
    public GlobalDefaultExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    private static ExceptionResource getExceptionResource(HttpServletRequest request, Exception ex, String message) {
        String requestURI = Optional.ofNullable(request).map(HttpServletRequest::getRequestURI).orElse("");
        String method = Optional.ofNullable(request).map(HttpServletRequest::getMethod).orElse("");
        log.error("Method: \"{}\"; URI: \"%{}\" ", method, requestURI);
        log.error(ex.getMessage(), ex);
        ExceptionResource result = new ExceptionResource();
        result.setUrl(requestURI);
        result.setExceptionClass(ex.getClass().getName());
        result.setMethod(method);
        result.setMessage(message);
        return result;
    }

    private ExceptionResource getExceptionResourceLocalized(HttpServletRequest request, Exception ex) {
        return getExceptionResource(request,ex,localize(ex));
    }

    private ExceptionResource getExceptionResource(HttpServletRequest request, Exception ex) {
        return getExceptionResource(request,ex,ex.getMessage());
    }

    private String localize(Exception e){
        return messageSource.getMessage(e.getMessage(), null, e.getMessage(), LocaleContextHolder.getLocale());
    };

    @ExceptionHandler(value = NotFoundParcomException.class)
    public ResponseEntity<ExceptionResource> NotFoundParcomException(HttpServletRequest request, NotFoundParcomException ex) {
             return new ResponseEntity<>(getExceptionResourceLocalized(request, ex),HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ExceptionResource> handleAccessDeniedException(HttpServletRequest request, ParcomException ex) {
        return new ResponseEntity<>(getExceptionResourceLocalized(request, ex),HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = {ForbiddenParcomException.class})
    public ResponseEntity<ExceptionResource> handleForbiddenParcomException(HttpServletRequest request, ParcomException ex) {
        return new ResponseEntity<>(getExceptionResourceLocalized(request, ex),HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = ParcomException.class)
    public ResponseEntity<ExceptionResource> handleParcomException(HttpServletRequest request, ParcomException ex) {
        return new ResponseEntity<>(getExceptionResourceLocalized(request, ex),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResource> handleException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(getExceptionResource(request, ex),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mess = "";
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            mess = mess + messageSource.getMessage(error, LocaleContextHolder.getLocale()) + ";  ";
        }
        return new ResponseEntity<>(getExceptionResource(null, ex, mess),HttpStatus.BAD_REQUEST);
    }





}
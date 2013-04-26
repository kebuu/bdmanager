package com.cta.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cta.config.AppConfig;
import com.cta.exception.AppException;
import com.cta.exception.ValidationServiceException;
import com.cta.tools.i18n.ImprovedMessageSource;

@ControllerAdvice
@Setter
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
	protected AppConfig appConfig;
	protected ImprovedMessageSource messageSource;
	
    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ExceptionInfo exceptionInfo = getExceptionInfo(ex, appConfig.isDebugModeActive());
        log.error(exceptionInfo.toString());
        return new ResponseEntity<>(exceptionInfo, getStatus(ex));
    }
    
   private HttpStatus getStatus(Exception ex) {
		if(ex instanceof AppException) {
			return HttpStatus.BAD_REQUEST;
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

   protected ExceptionInfo getExceptionInfo(Exception ex, boolean isDebugModeActive) {
	   String exceptionCode = AppException.DEFAULT_EXCEPTION_CODE;
	   Object[] exceptionMessageArguments = ArrayUtils.EMPTY_OBJECT_ARRAY;
	   
	   if(ex instanceof AppException) {
		   exceptionCode = ((AppException) ex).getCode();
		   exceptionMessageArguments = ((AppException) ex).getArguments();
       } 
	   String exceptionMessage = messageSource.getMessage(exceptionCode, exceptionMessageArguments);
	   
	   if(ex instanceof ValidationServiceException) {
		   return new ValidationServiceExceptionInfo(exceptionCode, exceptionMessage, ((ValidationServiceException) ex).getValidationResult());
	   } else {
		   if(isDebugModeActive) {
			   return new DetailedExceptionInfo(exceptionCode, exceptionMessage, ex);
		   } else {
			   return new ExceptionInfo(exceptionCode, exceptionMessage);
		   }
	   }
   }    
}
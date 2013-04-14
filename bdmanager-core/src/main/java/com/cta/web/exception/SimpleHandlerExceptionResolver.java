package com.cta.web.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.cta.config.AppConfig;
import com.cta.exception.AppException;
import com.cta.exception.ValidationServiceException;
import com.cta.tools.i18n.ImprovedMessageSource;

@Setter
@Slf4j
public class SimpleHandlerExceptionResolver implements HandlerExceptionResolver {

	protected AppConfig appConfig;
	protected ImprovedMessageSource messageSource;
	
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    	MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        Map<String,Object> model = new HashMap<String,Object>();
        ExceptionInfo exceptionInfo = getExceptionInfo(ex, appConfig.isDebugModeActive());
        response.setStatus(getStatus(ex));
		model.put("error", exceptionInfo);
        log.error(exceptionInfo.toString());
        return new ModelAndView(view, model);
    }
    
   private int getStatus(Exception ex) {
		if(ex instanceof AppException) {
			return HttpServletResponse.SC_BAD_REQUEST;
		} else {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
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

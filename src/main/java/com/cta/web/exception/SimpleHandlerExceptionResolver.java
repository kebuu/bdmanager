package com.cta.web.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.cta.config.AppConfig;
import com.cta.exception.AppException;

@Setter
public class SimpleHandlerExceptionResolver implements HandlerExceptionResolver {

	protected AppConfig appConfig;
	protected MessageSource messageSource;
	protected LocaleResolver localResolver;
	
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    	MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("error", getExceptionInfo(ex, appConfig.isDebugModeActive(), request));
        return new ModelAndView(view, model);
    }
    
   protected ExceptionInfo getExceptionInfo(Exception ex, boolean isDebugModeActive, HttpServletRequest request) {
	   String exceptionCode = AppException.DEFAULT_EXCEPTION_CODE;
	   Object[] exceptionMessageArguments = ArrayUtils.EMPTY_OBJECT_ARRAY;
	   
	   if(ex instanceof AppException) {
		   exceptionCode = ((AppException) ex).getCode();
		   exceptionMessageArguments = ((AppException) ex).getArguments();
       } 
	   
	   String exceptionMessage = messageSource.getMessage(exceptionCode, exceptionMessageArguments, localResolver.resolveLocale(request));
	   
	   if(isDebugModeActive) {
		   return new DetailedExceptionInfo(exceptionCode, exceptionMessage, ex);
	   } else {
		   return new ExceptionInfo(exceptionCode, exceptionMessage);
	   }
   }
}

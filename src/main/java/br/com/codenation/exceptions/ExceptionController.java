package br.com.codenation.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseError thrownBadRequestException(BadRequestException e, WebRequest webRequest) {
        return ResponseError.builder()
                .message(e.getMessage())
                .detail(e.getDetail())
                .timesTamp(System.currentTimeMillis())
                .path(webRequest.getDescription(true))
                .build();
    }
    
    @Override
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
    	List<Error> errors = createErrorList(ex.getBindingResult());
    	return handleExceptionInternal(ex, errors, headers, status, request);
    }
    
    private List<Error> createErrorList(BindingResult bindingResult) {
    	List<Error> errors = new ArrayList<>();
    	for (FieldError fieldError : bindingResult.getFieldErrors()) {
    		String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
    		String detail = fieldError.toString();
    		errors.add(new Error(message, detail));
    	}   	
    	return errors;
    }

    @Getter
	@AllArgsConstructor
    public static class Error {
    	private final String message;
    	private final String detail;
    }
    

}
package br.com.codenation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError thrownBadRequestException(BadRequestException e, WebRequest webRequest) {
        return ResponseError.builder()
                .mensagem(e.getMessage())
                .detalhe(e.getDetail())
                .timesTamp(System.currentTimeMillis())
                .path(webRequest.getDescription(true))
                .build();
    }

}
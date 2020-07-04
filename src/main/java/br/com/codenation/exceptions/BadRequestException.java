package br.com.codenation.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final String detail;
    private final Integer status;

    public BadRequestException(String message, String detail, Integer status) {
        super(message);
        this.detail = detail;
        this.status = status;
    }

}
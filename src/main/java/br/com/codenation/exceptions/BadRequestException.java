package br.com.codenation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final String detail;

}
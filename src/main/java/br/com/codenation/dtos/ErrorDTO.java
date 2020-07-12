package br.com.codenation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDTO {
	private final String message;
	private final String detail;
}

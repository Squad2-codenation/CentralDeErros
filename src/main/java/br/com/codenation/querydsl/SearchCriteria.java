package br.com.codenation.querydsl;

import lombok.*;

@Getter
@Setter
@Builder
public class SearchCriteria {
	private String key;
	private String operation;
	private Object value;
}

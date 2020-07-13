package br.com.codenation.querydsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class LogPredicatesBuilder {
	private List<SearchCriteria> params;
	
	public LogPredicatesBuilder() {
		this.params = new ArrayList<>();
	}
	
	public LogPredicatesBuilder with(String key, String operation, Object value) {
		params.add(SearchCriteria.builder()
				.key(key)
				.operation(operation)
				.value(value)
				.build());
		
		return this;
	}
	
	public BooleanExpression build() {
		if (params.isEmpty()) {
			return null;
		}

		List<BooleanExpression> predicates = params.stream()
				.map(param -> {
					LogPredicate predicate = new LogPredicate(param);
					return predicate.getPredicate();
				}).filter(Objects::nonNull).collect(Collectors.toList());
					
		BooleanExpression result = Expressions.asBoolean(true).isTrue();
		
		for (BooleanExpression predicate : predicates) {
			result = result.and(predicate);
		}
		
		return result;
	}
}

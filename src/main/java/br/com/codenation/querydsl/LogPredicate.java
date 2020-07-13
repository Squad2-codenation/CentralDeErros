package br.com.codenation.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.PathBuilder;

import br.com.codenation.entities.Application;
import br.com.codenation.entities.Log;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogPredicate {	
	private SearchCriteria criteria;
	
	public BooleanExpression getPredicate() {
        PathBuilder<Log> entityPath = new PathBuilder<>(Log.class, "log");
        
        switch (criteria.getKey()) {
        	case "environment":
        		return getEnvironment(entityPath);
        	case "level":
        		return getLevel(entityPath);
        	case "application":
        		return getApplication(entityPath);
        }
        
        return null;
	}
	
	private BooleanExpression getEnvironment(PathBuilder<Log> entityPath) {
		EnumPath<EnvironmentEnum> path = entityPath.getEnum(criteria.getKey(), EnvironmentEnum.class);
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            return path.eq(EnvironmentEnum.valueOf(criteria.getValue().toString()));
        }   
        return null;
	}

	private BooleanExpression getLevel(PathBuilder<Log> entityPath) {
		EnumPath<LevelEnum> path = entityPath.getEnum(criteria.getKey(), LevelEnum.class);
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            return path.eq(LevelEnum.valueOf(criteria.getValue().toString()));
        }   
        return null;
	}

	private BooleanExpression getApplication(PathBuilder<Log> entityPath) {
    	PathBuilder<Application> path = entityPath.get(criteria.getKey(), Application.class);
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            return path.get("name").eq(criteria.getValue().toString());
        }   
        return null;
	}
}

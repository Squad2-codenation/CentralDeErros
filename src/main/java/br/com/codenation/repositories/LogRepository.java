package br.com.codenation.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import br.com.codenation.entities.Log;
import br.com.codenation.entities.QLog;
	
@Repository
public interface LogRepository extends JpaRepository<Log, UUID>, QuerydslPredicateExecutor<Log>, QuerydslBinderCustomizer<QLog> {
	@Query("SELECT count(t) " +
            "FROM log t " +
            "JOIN log l ON l.id = t.id " +
            "OR (l.archived = false " +
            "AND l.level = t.level " +
            "AND l.environment = t.environment " +
            "AND l.title = t.title) " +
            "WHERE t.id = :logId ")
    Long countEvents(@Param("logId") UUID logId);
	
	@Override	
	default void customize(QuerydslBindings bindings, QLog log) {
		bindings.bind(String.class)	
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
	}
  
    @Query("SELECT l FROM log l WHERE l.archived = false")
    Page<Log> findAllNotArchived(Pageable pageable);
}

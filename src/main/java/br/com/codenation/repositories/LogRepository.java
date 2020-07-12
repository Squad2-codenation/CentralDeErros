package br.com.codenation.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.codenation.entities.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> {

    @Query("SELECT count(t) " +
            "FROM log t " +
            "JOIN log l ON l.id = t.id " +
            "OR (l.archived = false " +
            "AND l.level = t.level " +
            "AND l.environment = t.environment " +
            "AND l.title = t.title) " +
            "WHERE t.id = :logId ")
    Long countEvents(@Param("logId") UUID logId);

    @Query("SELECT l FROM log l WHERE l.archived = false")
    List<Log> findAllNotArchived();
}

package br.com.codenation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.codenation.entities.Log;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID>, JpaSpecificationExecutor<Log> {
}

package ${package}.repository;

import ${package}.model.${serviceName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Pattern — acceso a datos.
 */
@Repository
public interface ${serviceName}Repository extends JpaRepository<${serviceName}, Long> {
}

package butchersgarden.main.auth.microservices.authentication.repository;

import butchersgarden.main.auth.microservices.authentication.entity.Functions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<Functions, Long> {
}

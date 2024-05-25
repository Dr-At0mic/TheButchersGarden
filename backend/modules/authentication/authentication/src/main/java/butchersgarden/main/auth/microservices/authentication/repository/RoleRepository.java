package butchersgarden.main.auth.microservices.authentication.repository;

import butchersgarden.main.auth.microservices.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

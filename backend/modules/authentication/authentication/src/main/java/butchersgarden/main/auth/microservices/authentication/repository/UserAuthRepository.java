package butchersgarden.main.auth.microservices.authentication.repository;

import butchersgarden.main.auth.microservices.authentication.entity.User;
import butchersgarden.main.auth.microservices.authentication.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthentication, Integer> {

    UserAuthentication findByUser(User user);

}

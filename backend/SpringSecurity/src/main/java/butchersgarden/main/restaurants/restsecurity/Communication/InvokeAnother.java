package butchersgarden.main.restaurants.restsecurity.Communication;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvokeAnother {

    public Optional<User> getUser(String userName) {
//        return User.builder().build();
        return null;
    }
}

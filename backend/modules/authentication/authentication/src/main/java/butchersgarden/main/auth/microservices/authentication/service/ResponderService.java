package butchersgarden.main.auth.microservices.authentication.service;

import butchersgarden.main.auth.microservices.authentication.entity.User;
import butchersgarden.main.auth.microservices.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ResponderService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String emailAddress) {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (null == user)
            throw new RuntimeException("no User Found");
        return user;
    }
}

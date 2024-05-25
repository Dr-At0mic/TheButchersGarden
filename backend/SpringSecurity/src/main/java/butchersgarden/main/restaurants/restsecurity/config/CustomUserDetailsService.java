package butchersgarden.main.restaurants.restsecurity.config;
import butchersgarden.main.restaurants.restsecurity.models.User_DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    @Autowired
    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User_DTO user = restTemplate.getForObject("http://AUTH-RESPONDER/auth-responder/GetEntity?emailAddress=" + email, User_DTO.class);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            return new User(
                    email,
                    "",
                    user.isAccountStatus(),
                    true, true, true,
                    null!=user.getRoleList()?user.getRoleList().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
                            .collect(Collectors.toList())
                            : new ArrayList<>()
            );
        } catch (Exception e) {
            System.out.println("Service Currently Unavailable" + e);
            throw new RuntimeException(e);
        }
    }
}

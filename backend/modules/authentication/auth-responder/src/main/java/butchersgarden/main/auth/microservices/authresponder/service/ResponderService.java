package butchersgarden.main.auth.microservices.authresponder.service;

import butchersgarden.main.auth.microservices.authresponder.entity.User;
import butchersgarden.main.auth.microservices.authresponder.model.User_DTO;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ResponderService {

    @Autowired
    private RestTemplate restTemplate;

    public User_DTO getUserRoles(String emailAddress) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Custom-Header", "header-value");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            User user = restTemplate.exchange("http://localhost:8091/response/getUserEntity?emailAddress=" + emailAddress, HttpMethod.GET, entity, User.class).getBody();
            if(null == user)
                throw new RuntimeException("no user Found");
            return User_DTO.builder().roleList(user.getRoleList()).accountStatus(user.isAccountStatus()).build();
        } catch (Exception e) {
            System.out.println("Service unavailable. Please try again later. Error: " + e);
        }
        return User_DTO.builder().build();
    }
}

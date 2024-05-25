package butchersgarden.main.auth.microservices.authentication.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InvokeAnotherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TaskExecutor taskExecutor;

    public void SendVerificationMail(String emailAddress, String token) {
        String requestBody = "{\"emailAddress\": \"" + emailAddress + "\", \"activationToken\": \"" + token + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            taskExecutor.execute(() -> {
                try {
                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                            "http://mail-sender/mail/sendVerificationEmail",
                            requestEntity,
                            String.class
                    );
                    System.out.println(responseEntity.getBody());
                } catch (Exception e) {
                    System.err.println("Error occurred while sending verification email: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.err.println("Error occurred while Threading task: " + e.getMessage());
        }
    }
}

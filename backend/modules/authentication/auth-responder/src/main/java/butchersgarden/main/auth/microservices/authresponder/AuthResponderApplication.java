package butchersgarden.main.auth.microservices.authresponder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthResponderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthResponderApplication.class, args);
	}

}

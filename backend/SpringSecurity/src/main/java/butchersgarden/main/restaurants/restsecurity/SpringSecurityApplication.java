package butchersgarden.main.restaurants.restsecurity;

import butchersgarden.main.restaurants.restsecurity.config.ReservationConfig;
import butchersgarden.main.restaurants.restsecurity.config.TableReserveConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties({TableReserveConfig.class, ReservationConfig.class})
@CrossOrigin
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}

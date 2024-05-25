package butchersgarden.main.restaurants.microservices.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReservationsApplication {

	public static void main(String[] args) {
		System.out.println();
		SpringApplication.run(ReservationsApplication.class, args);
	}

}
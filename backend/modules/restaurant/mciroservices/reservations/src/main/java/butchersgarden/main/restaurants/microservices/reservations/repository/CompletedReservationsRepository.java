package butchersgarden.main.restaurants.microservices.reservations.repository;

import butchersgarden.main.restaurants.microservices.reservations.entity.CompletedReservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedReservationsRepository extends JpaRepository<CompletedReservations, Long> {
}

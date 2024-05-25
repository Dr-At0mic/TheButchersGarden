package butchersgarden.main.restaurants.microservices.reservations.repository;

import butchersgarden.main.restaurants.microservices.reservations.entity.ReservationChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationChartRepository extends JpaRepository<ReservationChart,Long> {

    List<ReservationChart> findByUserId(Long userId);
}

package butchersgarden.main.restaurants.microservices.reservations.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_chart")
@Entity
public class ReservationChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime bookingTime;
    @Column(nullable = false)
    private LocalDateTime bookingStarts;
    @Column(nullable = false)
    private LocalDateTime bookingEnd;
    @Column(nullable = false)
    private int tableCount;
    @Column(nullable = false)
    private Long tableId;
}
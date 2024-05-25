package butchersgarden.main.restaurants.microservices.reservations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "completed_reservations")
@Entity
public class CompletedReservations {
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

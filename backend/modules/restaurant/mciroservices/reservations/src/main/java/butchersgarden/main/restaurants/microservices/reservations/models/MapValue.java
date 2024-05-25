package butchersgarden.main.restaurants.microservices.reservations.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapValue {
    private int count;
    private Long resId;
    private LocalDateTime bookingStarts;
    private LocalDateTime bookingEnds;
}

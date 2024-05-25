package com.restutils.CommonUtils.reservationmodles;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditReservationRequest {
    private Long userId;
    @NotNull(message = "reservationId required")
    private Long reservationId;
    @NotNull(message = "count required")
    private int count;
    @NotNull(message = "tableId required")
    private Long tableId;
    @NotNull(message = "editBookingStart time is required")
    private String editStart;
    @NotNull(message = "editBookingEnd time is required")
    private String editEnd;
}

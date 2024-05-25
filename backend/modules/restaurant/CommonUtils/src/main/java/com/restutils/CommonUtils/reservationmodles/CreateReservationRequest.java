package com.restutils.CommonUtils.reservationmodles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequest {
    private Long userId;
    @NotNull(message = "tableId required")
    private Long tableId;
    private int count;
    @NotBlank(message = "price is mandatory")
    private String price;
    @NotBlank(message = "bookingStarts is mandatory")
    private String bookingStarts;
    @NotBlank(message = "bookingEnd is mandatory")
    private String bookingEnd;

}

package butchersgarden.main.restaurants.microservices.reservations.controller;

import butchersgarden.main.restaurants.microservices.reservations.component.ReservationComponent;
import com.restutils.CommonUtils.reservationmodles.CreateReservationRequest;
import com.restutils.CommonUtils.reservationmodles.EditReservationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//    add base64 encoding to every data which is being sent to server not for security just for making it look cooler ;
//    separate the reservation chart into two tables and reservation charts and do one to one mapping and be careful of the stackoverflow error ;
//    write a scheduler to shift all the transaction to completed reservations ;
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationComponent reservationComponent;

    @GetMapping("/availableTables")
    public Response availableTables() {
        return reservationComponent.availableTables();
    }

    @GetMapping("/reservedTables")
    public Response reservedTables(@Valid @RequestParam Long userId) {
        return reservationComponent.reservedTable(userId);
    }

    @PostMapping("/reserveTable")
    public Response reserveTable(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        return reservationComponent.reserveTable(createReservationRequest);
    }

    @PostMapping("/editReservation")
    public Response editReservations(@Valid @RequestBody EditReservationRequest editReservationRequests) {
        return reservationComponent.editReservations(editReservationRequests);
    }

    @PostMapping("/cancelReservation")
    public Response cancelReservation(@Valid @RequestBody Long reservationId) {
        return reservationComponent.cancelReservation(reservationId);
    }

}

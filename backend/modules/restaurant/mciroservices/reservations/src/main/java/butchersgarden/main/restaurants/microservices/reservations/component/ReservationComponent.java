package butchersgarden.main.restaurants.microservices.reservations.component;

import butchersgarden.main.restaurants.microservices.reservations.entity.ReservationChart;
import butchersgarden.main.restaurants.microservices.reservations.service.Filters;
import butchersgarden.main.restaurants.microservices.reservations.service.ReservationService;
import com.restutils.CommonUtils.reservationmodles.CreateReservationRequest;
import com.restutils.CommonUtils.reservationmodles.EditReservationRequest;
import com.utils.AppCommonUtils.models.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationComponent {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private Filters filters;

    public Response availableTables() {
        return reservationService.availableTables();
    }

    public Response reservedTable(Long userId) {
        return reservationService.reservedTable(userId);
    }

    public Response reserveTable(CreateReservationRequest createReservationRequest) {
        return reservationService.reserveTable(createReservationRequest);
    }


    public Response editReservations(EditReservationRequest editReservationRequests) {
        List<Optional<ReservationChart>> reservationChart = reservationService.getReservationChart(List.of(editReservationRequests.getReservationId()));
        if (reservationChart.getFirst().isEmpty())
            return Response.builder()
                    .message("Cannot Edit a Null Entity")
                    .build();
        if (editReservationRequests.getTableId().equals(reservationChart.getFirst().get().getTableId()) && editReservationRequests.getCount() == reservationChart.getFirst().get().getTableCount())
            return Response.builder()
                    .message("Nothing to update both are same")
                    .build();
        if (editReservationRequests.getTableId().equals(reservationChart.getFirst().get().getTableId()) && editReservationRequests.getCount() < reservationChart.getFirst().get().getTableCount())
            return reservationService.editReservations(reservationChart.getFirst().get(), editReservationRequests);
        if (!filters.isTableAvailable(editReservationRequests.getTableId(), editReservationRequests.getCount(), filters.getActiveReservationsForTableAndTimeRange(reservationService.getAll(), editReservationRequests.getTableId(),
                LocalDateTime.now(), LocalDateTime.now())))
            return Response.builder()
                    .message("Table Not Available")
                    .build();
        else return reservationService.editReservations(reservationChart.getFirst().get(), editReservationRequests);
    }


    public Response cancelReservation(Long reservationId) {
        List<Optional<ReservationChart>> optionalList = reservationService.getReservationChart(List.of(reservationId));
        Response response = Response.builder().status(true).build();
        for (Optional<ReservationChart> optionalReservationChart : optionalList) {
            if (optionalReservationChart.isEmpty())
                return Response.builder()
                        .status(false)
                        .message("Cannot cancel a Absent Entity")
                        .httpStatus(HttpStatus.NO_CONTENT)
                        .build();

            if (response.isStatus())
                response = reservationService.cancelReservation(optionalReservationChart.get());

        }
        return response;
    }

}
//    bugs bugs bugs i don't like it but bugs likes me
//    public Response editReservations(List<EditReservationRequest> editReservationRequests) {
//        List<Optional<ReservationChart>> optionalList = editReservationRequests.stream()
//                .map(editReservationRequest -> reservationService
//                        .getReservationChart(List.of(editReservationRequest
//                                .getReservationId())).getFirst())
//                .toList();
//        Response response = Response.builder()
//                .status(true)
//                .build();
//        for (Optional<ReservationChart> optionalReservationChart : optionalList) {
//            if (optionalReservationChart.isEmpty())
//                return Response.builder()
//                        .status(false)
//                        .message("Entity Not found to update")
//                        .build();
//            List<Optional<ReservationChart>> optionalsReservationChart = editReservationRequests.stream()
//                    .filter(editReservation -> editReservation
//                            .getReservationId().equals(optionalReservationChart.get().getResId()))
//                    .map(editReservationReq -> {
//                        optionalReservationChart.get().setTableCount(editReservationReq.getCount());
//                        optionalReservationChart.get().setTableId(editReservationReq.getTableId());
//                        return optionalReservationChart;
//                    })
//                    .toList();
//            if (response.isStatus() ){
//                response = reservationService.editReservations(optionalsReservationChart);
//
//            }
//            else break;
//        }
//        return response;
//    }

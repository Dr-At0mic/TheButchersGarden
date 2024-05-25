package butchersgarden.main.restaurants.microservices.reservations.service;

import butchersgarden.main.restaurants.microservices.reservations.entity.ReservationChart;
import butchersgarden.main.restaurants.microservices.reservations.repository.ReservationChartRepository;
import com.restutils.CommonUtils.reservationmodles.CreateReservationRequest;
import com.restutils.CommonUtils.reservationmodles.EditReservationRequest;
import com.restutils.CommonUtils.tablemodels.entitypojo.TablePojo;
import com.utils.AppCommonUtils.models.response.Response;
import com.utils.AppCommonUtils.systemConstants.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ReservationService {

    @Autowired
    private ReservationChartRepository reservationChartRepository;

    @Autowired
    private Filters filters;

    public Response availableTables() {
        List<ReservationChart> reservationCharts = reservationChartRepository.findAll();
        if (reservationCharts.isEmpty())
            return Response.builder()
                    .message("AvailableTables")
                    .status(true)
                    .data(filters.getAllTables())
                    .build();
        List<TablePojo> availableTables = filters.reservationMinusAllTables(reservationCharts);
        return Response.builder()
                .data(availableTables)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Response reservedTable(Long userId) {
        List<ReservationChart> reservationCharts = reservationChartRepository.findByUserId(userId);
        if (reservationCharts.isEmpty())
            return Response.builder()
                    .status(true)
                    .message("no reservation yet")
                    .httpStatus(HttpStatus.OK)
                    .build();
        return Response.builder()
                .status(true)
                .message("Reserved tables")
                .data(filters.swapTableCountXResCount(filters.reservedTable(reservationCharts)))
                .build();
    }

    //  add the time filter so that a user can reserve table time based
    public Response reserveTable(CreateReservationRequest createReservationRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime bookingStarts = LocalDateTime.parse(createReservationRequest.getBookingStarts(), formatter);
        LocalDateTime bookingEnds = LocalDateTime.parse(createReservationRequest.getBookingEnd(), formatter);
        List<ReservationChart> reservationCharts = reservationChartRepository.findAll();
        if (!filters.isTableAvailable(createReservationRequest.getTableId(), createReservationRequest.getCount(),
                filters.getActiveReservationsForTableAndTimeRange(reservationCharts, createReservationRequest.getTableId(),
                        bookingStarts, bookingEnds)))
            return Response.builder()
                    .ErrorCode(ErrorCode.TRANSACTIONAL_ERROR)
                    .message("no Table available")
                    .build();
        try {
            reservationChartRepository.save(ReservationChart.builder()
                    .tableId(createReservationRequest.getTableId())
                    .bookingTime(LocalDateTime.now())
                    .bookingStarts(bookingStarts)
                    .bookingEnd(bookingEnds)
                    .tableId(createReservationRequest.getTableId())
                    .userId(createReservationRequest.getUserId())
                    .tableCount(createReservationRequest.getCount())
                    .build());
            return Response.builder()
                    .status(true)
                    .message("Table Reserved Successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (TransactionalException te) {
            return Response.builder()
                    .status(true)
                    .message(te.getMessage())
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

    }

    public List<Optional<ReservationChart>> getReservationChart(List<Long> reservationID) {
        return reservationID.stream()
                .map(aLong -> reservationChartRepository.findById(aLong))
                .collect(Collectors.toList());
    }

    //    here add filters like if the cancelling time is after the reservation end then return response cannot be cancelled
//    and during the implementation of payment the cancelling of reservation wil not return full money only the 50%
    public Response cancelReservation(ReservationChart reservationChart) {
        try {
            reservationChartRepository.delete(reservationChart);
            return Response.builder()
                    .status(true)
                    .httpStatus(HttpStatus.OK)
                    .message("cancellation Success")
                    .build();
        } catch (TransactionalException te) {
            return Response.builder()
                    .httpStatus(HttpStatus.EXPECTATION_FAILED)
                    .message("Oops Something Went Wrong")
                    .build();
        }
    }

    //  check weather the reservation is finished or not then only update or else don't allow to edit
//    public Response editReservations(List<Optional<ReservationChart>> editReservationRequests) {
//        try {
//            for (Optional<ReservationChart> reservationChart : editReservationRequests) {
//                if (reservationChart.isEmpty())
//                    return Response.builder()
//                            .message("Entity is Empty")
//                            .httpStatus(HttpStatus.NO_CONTENT)
//                            .build();
//                Optional<ReservationChart> beforeEdit = reservationChartRepository.findById(reservationChart.get().getResId());
//                if (beforeEdit.isEmpty())
//                    return Response.builder()
//                            .message("Entity is Empty")
//                            .httpStatus(HttpStatus.NO_CONTENT)
//                            .build();
//
//                if ( (beforeEdit.get().getTableId().equals(reservationChart.get().getTableId()) || beforeEdit.get().getTableCount() < reservationChart.get().getTableCount()) &&  (!filters.isTableAvailable(reservationChart.get().getTableId(), reservationChart.get().getTableCount(),
//                        filters.getActiveReservationsForTableAndTimeRange(reservationChartRepository.findAll(), reservationChart.get().getTableId(),
//                                reservationChart.get().getBookingStarts(), reservationChart.get().getBookingEnd())))){
//                    return Response.builder()
//                            .message("no Table available")
//                            .build();
//                }
//                else{
//                    reservationChartRepository.save(reservationChart.get());
//                }
//                return Response.builder()
//                        .status(true)
//                        .message("Reservation Updated")
//                        .httpStatus(HttpStatus.OK)
//                        .build();
//            }
//            return Response.builder().build();
//        } catch (TransactionalException te) {
//            return Response.builder()
//                    .message("oops Something Went Wrong")
//                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build();
//        }
//    }

    public Response editReservations(ReservationChart editReservationChart,EditReservationRequest editReservationRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime bookingStarts = LocalDateTime.parse(editReservationRequest.getEditStart(), formatter);
        LocalDateTime bookingEnds = LocalDateTime.parse(editReservationRequest.getEditEnd(), formatter);
        editReservationChart.setTableCount(editReservationRequest.getCount());
        editReservationChart.setTableId(editReservationChart.getTableId());
        editReservationChart.setBookingStarts(bookingStarts);
        editReservationChart.setBookingEnd(bookingEnds);
        reservationChartRepository.save(editReservationChart);
        return Response.builder()
                .status(true)
                .message("table Edited Success Fully")
                .build();
    }

    public List<ReservationChart> getAll(){
        return reservationChartRepository.findAll();
    }
}

package butchersgarden.main.restaurants.microservices.reservations.service;

import butchersgarden.main.restaurants.microservices.reservations.entity.ReservationChart;
import butchersgarden.main.restaurants.microservices.reservations.models.MapValue;
import com.restutils.CommonUtils.tablemodels.entitypojo.TablePojo;
import com.restutils.CommonUtils.tablemodels.response.ResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Filters {

    @Autowired
    private RestTemplate restTemplate;

    public List<TablePojo> getAllTables() {
        try {
            ResponseEntity<ResponseList> response = null;
            response = restTemplate.getForEntity("http://TABLE-RESERVE/table/getAllTable", ResponseList.class);
            List<TablePojo> tablelist;
            tablelist = (List<TablePojo>) response.getBody().getData();
            return tablelist;
        } catch (Exception e) {
            throw new RuntimeException("Service Unavailable");
        }
    }

    public List<TablePojo> getAllTablesById(List<Long> tableIds) {
        try {
            ResponseEntity<ResponseList> response = null;
            response = restTemplate.postForEntity("http://TABLE-RESERVE/table/getTable", tableIds, ResponseList.class);
            List<TablePojo> tablelist = null;
            tablelist = (List<TablePojo>) response.getBody().getData();
            return tablelist;
        } catch (Exception e) {
            throw new RuntimeException("Service Unavailable");
        }
    }


    public Map<Long, MapValue> reservedTable(List<ReservationChart> reservationCharts) {
        Map<Long, MapValue> reservedTable = new HashMap<>();
        reservationCharts.forEach(reservationChart -> {
            if (reservedTable.isEmpty() || !reservedTable.containsKey(reservationChart.getTableId()))
                reservedTable.put(reservationChart.getTableId(), MapValue.builder().bookingStarts(reservationChart.getBookingStarts()).bookingEnds(reservationChart.getBookingEnd()).resId(reservationChart.getResId()).count(reservationChart.getTableCount()).build());
            else
                reservedTable.put(reservationChart.getTableId(), MapValue.builder().bookingStarts(reservationChart.getBookingStarts()).bookingEnds(reservationChart.getBookingEnd()).resId(reservationChart.getResId()).count(reservedTable.get(reservationChart.getTableId()).getCount() + reservationChart.getTableCount()).build());
        });
        return reservedTable;
    }

    public List<Long> getAllKeys(Map<Long, MapValue> reservedTables) {
        return new ArrayList<>(reservedTables.keySet());
    }

    public List<TablePojo> reservationMinusAllTables(List<ReservationChart> reservationCharts) {
        Map<Long, MapValue> reservedTable = reservedTable(reservationCharts);
        List<TablePojo> tablesList = getAllTables();
        tablesList.forEach(table -> {
            reservedTable.forEach((aLong, mapValue) -> {
                if (table.getTableId().equals(aLong))
                    table.setTableCount(table.getTableCount() - mapValue.getCount());
            });
        });
        return tablesList;
    }

    public List<TablePojo> swapTableCountXResCount(Map<Long, MapValue> reservedTables) {
        List<TablePojo> tableList = getAllTablesById(getAllKeys(reservedTables));
        return tableList.stream().peek(tablePojo -> {
            if (reservedTables.containsKey(tablePojo.getTableId())) {
                tablePojo.setTableCount(reservedTables.get(tablePojo.getTableId()).getCount());
                tablePojo.setResId(reservedTables.get(tablePojo.getTableId()).getResId());
                tablePojo.setBookingStarts(reservedTables.get(tablePojo.getTableId()).getBookingStarts());
                tablePojo.setBookingEnds(reservedTables.get(tablePojo.getTableId()).getBookingEnds());

            }
        }).collect(Collectors.toList());
    }

    public boolean isTableAvailable(Long tableId, int count, List<ReservationChart> reservationCharts) {
        if (!reservationCharts.isEmpty()) {
            Map<Long, MapValue> reservedTable = reservedTable(reservationCharts);
            List<TablePojo> tableList = getAllTablesById(List.of(tableId));
//            Integer reservedCount = reservedTable.get(tableId);
//            int reservedCountInt;
//            if (reservedCount != null) {
//                reservedCountInt = reservedCount.intValue();
//            } else {
//                reservedCountInt = 0;
//            }
            return tableList.getFirst().getTableCount() - reservedTable.get(tableId).getCount() >= count;
        } else {
            try {
                List<TablePojo> tableList = getAllTablesById(List.of(tableId));
                return tableList.getFirst().getTableCount() >= count;
            } catch (NoSuchElementException nee) {
                throw new RuntimeException("Table Not found Exception");
            }
        }
    }

    //    create a new filter and check weather the targetdate is between in reservation chart start and end time then add the chart , add filter for checking weather for target is same as the start or end and only take the trascation which is currently active
    public List<ReservationChart> getActiveReservationsForTableAndTimeRange(List<ReservationChart> reservationChartList, Long tableId, LocalDateTime startTime, LocalDateTime endTime) {
        List<ReservationChart> reservationCharts = new ArrayList<>();
        for (ReservationChart reservationChart : reservationChartList) {
            if (!reservationChart.getTableId().equals(tableId)) continue;
//            if(!((startTime.isBefore(reservationChart.getBookingStarts()) && startTime.isAfter(reservationChart.getBookingEnd())) && (endTime.isBefore(reservationChart.getBookingStarts()) && endTime.isAfter(reservationChart.getBookingTime())))) continue;
            reservationCharts.add(reservationChart);
        }
        System.out.println(reservationCharts);
        return reservationCharts;
    }

}

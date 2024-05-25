package butchersgarden.main.restaurants.microservices.tablereserve.service;

import butchersgarden.main.restaurants.microservices.tablereserve.entity.Table;
import butchersgarden.main.restaurants.microservices.tablereserve.entity.TableType;
import butchersgarden.main.restaurants.microservices.tablereserve.repository.TableRepository;
import butchersgarden.main.restaurants.microservices.tablereserve.repository.TableTypeRepository;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableModel;
import com.restutils.CommonUtils.tablemodels.entitypojo.TablePojo;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableTypePojo;
import com.restutils.CommonUtils.tablemodels.response.ResponseList;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private TableTypeRepository tableTypeRepository;

    public Response createTable(List<TableModel> tableModelList) {
        System.out.println(tableModelList);
        for (TableModel tableModel : tableModelList) {
            Long tableTypeId = tableModel.getTableTypeId();
            if (tableTypeId == null)
                return Response.builder().message("Table Type is not Specified").httpStatus(HttpStatus.OK).build();
            Optional<Table> existingTable = tableRepository.findTableByIsAcAndTableType(tableModel.isAc(), tableTypeId);
            if (existingTable.isPresent()) {
                return Response.builder().message("table Exist with same Specification Try to edit or delete it to create new ").data(List.of(existingTable.get())).httpStatus(HttpStatus.OK).build();
            }
            Optional<TableType> tableType = tableTypeRepository.findById(tableModel.getTableTypeId());
            if (tableType.isEmpty())
                return Response.builder().message("no TableType Found").build();
            System.out.println(tableRepository.save(Table.builder().tableType(tableType.get()).tableCount(tableModel.getTableCount()).isAc(tableModel.isAc()).tablePrice(tableModel.getTablePrice()).build()));
        }
        return Response.builder().message("Table Added SuccessFully").httpStatus(HttpStatus.OK).status(true).build();
    }

    public ResponseList getTableById(List<Long> tableIdList) {
        try {
            List<TablePojo> tablePojoList = tableIdList.stream()
                    .map(tableRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(this::toTablePojo)
                    .toList();
            return ResponseList.builder()
                    .status(true)
                    .httpStatus(HttpStatus.OK)
                    .data(tablePojoList)
                    .build();

        } catch (TransactionalException te) {
            throw new RuntimeException("oops something went wrong");
        }
    }

    private TablePojo toTablePojo(Table table) {
        return TablePojo.builder()
                .tableId(table.getTableId())
                .isAc(table.isAc())
                .tableType(toTableTypePojo(table.getTableType()))
                .tableCount(table.getTableCount())
                .tablePrice(table.getTablePrice())
                .build();
    }

    private TableTypePojo toTableTypePojo(TableType tableType) {
        return TableTypePojo.builder()
                .tableTypeId(tableType.getTableTypeId())
                .tableTypeName(tableType.getTableTypeName())
                .seats(tableType.getSeats())
                .build();
    }

    public ResponseList getAllTable() {
        try {
            List<TablePojo> tablePojoList = tableRepository.findAll().stream()
                    .map(this::toTablePojo)
                    .toList();
            return ResponseList.builder()
                    .status(true)
                    .httpStatus(HttpStatus.OK)
                    .data(tablePojoList)
                    .build();
        } catch (TransactionalException te) {
            throw new RuntimeException("oops something went wrong");
        }

    }

    public Response editTables(List<Table> tableList) {
        List<Table> editedTables = new ArrayList<>();
        for (Table table : tableList) {
            Optional<Table> optionalTable = tableRepository.findById(table.getTableId());
            if (optionalTable.isPresent()) {
                Table existingTable = optionalTable.get();
                existingTable.setAc(table.isAc());
                existingTable.setTablePrice(table.getTablePrice());
                existingTable.setTableCount(table.getTableCount());
                editedTables.add(tableRepository.save(existingTable));
            } else {
                throw new NotFoundException("Table Not Found Exception");
            }
        }
        return Response.builder()
                .status(true)
                .message("Tables edited successfully")
                .httpStatus(HttpStatus.OK)
                .data(editedTables)
                .build();
    }

    public Response deleteTablesById(List<Long> tableIds) {
        try {
            for (Long tableId : tableIds) {
                tableRepository.deleteById(tableId);
            }
            return Response.builder()
                    .status(true)
                    .message("Tables deleted successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (TransactionalException te) {
            throw new RuntimeException("Failed to delete tables");
        }
    }

    public Response deleteAllTables() {
        try {
            tableRepository.deleteAll();
            return Response.builder()
                    .status(true)
                    .message("All tables deleted successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete tables");
        }
    }


}

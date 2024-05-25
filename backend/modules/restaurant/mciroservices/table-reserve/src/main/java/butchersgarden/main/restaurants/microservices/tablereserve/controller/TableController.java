package butchersgarden.main.restaurants.microservices.tablereserve.controller;

import butchersgarden.main.restaurants.microservices.tablereserve.component.TableComponent;
import butchersgarden.main.restaurants.microservices.tablereserve.entity.Table;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableModel;
import com.restutils.CommonUtils.tablemodels.response.ResponseList;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/table")
public class TableController {

    @Autowired
    private TableComponent tableComponent;

    @PostMapping("/createTable")
    public ResponseEntity<Response> reserveTable(@Valid @RequestBody List<TableModel> tableModels) {
        return new ResponseEntity<Response>(tableComponent.createTable(tableModels), HttpStatus.OK);
    }

    @PostMapping("/getTable")
    public ResponseEntity<ResponseList> getTable(@Valid @RequestBody List<Long> tableIdList) {
        return new ResponseEntity<ResponseList>(tableComponent.getTableById(tableIdList), HttpStatus.OK);
    }

    @GetMapping("/getAllTable")
    public ResponseEntity<ResponseList> getAllTable() {
        return new ResponseEntity<ResponseList>(tableComponent.getAllTable(), HttpStatus.OK);
    }

    @PostMapping("/editTable")
    public ResponseEntity<Response> editTable(@Valid @RequestBody List<Table> tableList) {
        return new ResponseEntity<>(tableComponent.editTables(tableList), HttpStatus.OK);
    }


    @DeleteMapping("/deleteTable")
    public ResponseEntity<Response> deleteTablesById(@Valid @RequestBody List<Long> tableIds) {
        return new ResponseEntity<>(tableComponent.deleteTablesById(tableIds), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllTable")
    public ResponseEntity<Response> deleteAllTables() {
        return new ResponseEntity<>(tableComponent.deleteAllTables(), HttpStatus.OK);
    }

}

package butchersgarden.main.restaurants.restsecurity.controller;


import butchersgarden.main.restaurants.restsecurity.config.TableReserveConfig;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableModel;
import com.restutils.CommonUtils.tablemodels.entitypojo.TablePojo;
import com.utils.AppCommonUtils.models.response.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/table")

public class TableSecurityController {

    private final TableReserveConfig tableReserveConfig;

    @Autowired
    public TableSecurityController(TableReserveConfig tableReserveConfig) {
        this.tableReserveConfig = tableReserveConfig;
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/createTable")
    public Response reserveTable(@Valid @RequestBody List<TableModel> tableModels) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(tableReserveConfig.getUrl() + "createTable", tableModels, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }

    }

    @PostMapping("/getTable")
    public Response getTable(@Valid @RequestBody List<Long> tableIdList) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(tableReserveConfig.getUrl() + "getTable", tableIdList, Response.class);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }

    @GetMapping("/getAllTable")
    public Response getAllTable() {

        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.getForEntity(tableReserveConfig.getUrl() + "getAllTable", Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }


    @PostMapping("/editTable")
    public Response editTable(@Valid @RequestBody List<TablePojo> tableList) {
        ResponseEntity<Response> response = null;
        try {
            response = restTemplate.postForEntity(tableReserveConfig.getUrl() + "editTable", tableList, Response.class);
            return response.getBody();
        } catch (Exception e) {
            return Response.builder().data(e.getMessage()).build();
        }
    }


    @DeleteMapping("/deleteTable")
    public ResponseEntity<Response> deleteTable() {
        return new ResponseEntity<>(Response.builder()
                .status(true)
                .message("success")
                .httpStatus(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }
}

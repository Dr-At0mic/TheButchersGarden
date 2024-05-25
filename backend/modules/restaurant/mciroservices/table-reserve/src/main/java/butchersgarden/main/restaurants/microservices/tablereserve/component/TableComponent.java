package butchersgarden.main.restaurants.microservices.tablereserve.component;

import butchersgarden.main.restaurants.microservices.tablereserve.entity.Table;
import butchersgarden.main.restaurants.microservices.tablereserve.service.TableService;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableModel;
import com.restutils.CommonUtils.tablemodels.response.ResponseList;
import com.utils.AppCommonUtils.models.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TableComponent {
    @Autowired
    private TableService tableService;

    public Response createTable(List<TableModel> tableList) {
        return tableService.createTable(tableList);
    }

    public ResponseList getTableById(List<Long> tableIdList) {
        return tableService.getTableById(tableIdList);
    }

    public ResponseList getAllTable() {
        return tableService.getAllTable();
    }

    public Response editTables(List<Table> tableList) {
        return tableService.editTables(tableList);
    }

    public Response deleteTablesById(List<Long> tableIds) {
        return tableService.deleteTablesById(tableIds);
    }

    public Response deleteAllTables() {
        return tableService.deleteAllTables();
    }


}

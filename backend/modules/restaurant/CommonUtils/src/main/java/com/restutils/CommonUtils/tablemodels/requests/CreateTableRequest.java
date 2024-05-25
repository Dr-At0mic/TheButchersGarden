package com.restutils.CommonUtils.tablemodels.requests;
import com.restutils.CommonUtils.tablemodels.entitypojo.TableModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableRequest  {
    private List <TableModel> tableList;
}

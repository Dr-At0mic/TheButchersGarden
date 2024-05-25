package com.restutils.CommonUtils.tablemodels.entitypojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableTypePojo {
    private Long tableTypeId;
    private String tableTypeName;
    private int seats;
}

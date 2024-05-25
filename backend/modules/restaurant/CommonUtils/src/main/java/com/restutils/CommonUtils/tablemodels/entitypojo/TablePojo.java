package com.restutils.CommonUtils.tablemodels.entitypojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TablePojo {
    private Long resId;
    private Long tableId;
    private boolean isAc;
    private TableTypePojo tableType;
    private int tableCount;
    private String tablePrice;
    private LocalDateTime bookingStarts;
    private LocalDateTime bookingEnds;
}

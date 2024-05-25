package com.restutils.CommonUtils.tablemodels.entitypojo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableTypeModel {
    @NotBlank(message = "seats is mandatory")
    private String tableTypeName;
    @NotBlank(message = "seats is mandatory")
    private int seats;
}

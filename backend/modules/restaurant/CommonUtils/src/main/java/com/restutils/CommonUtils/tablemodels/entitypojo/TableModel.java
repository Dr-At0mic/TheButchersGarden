package com.restutils.CommonUtils.tablemodels.entitypojo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableModel{
    @JsonProperty
    private boolean isAc;
    private Long tableTypeId;
    @NotBlank(message = "tablePrice is Mandatory")
    private String tablePrice;
    private int tableCount;

}

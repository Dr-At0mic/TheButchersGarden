package com.restutils.CommonUtils.tablemodels.response;

import com.restutils.CommonUtils.tablemodels.entitypojo.TablePojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseList {
    private boolean status;
    private String message;
    private List<TablePojo> data;
    private HttpStatus httpStatus;
}

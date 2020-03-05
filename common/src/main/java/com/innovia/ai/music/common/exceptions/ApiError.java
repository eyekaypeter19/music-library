package com.innovia.ai.music.common.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {




    @ApiModelProperty(value = "(optional) Text that provide more details and corrective actions related to the error.")
    private String message;

    @ApiModelProperty(value = "(optional) http error code extension like 400-2")
    private Integer status;

    @ApiModelProperty(value = "(optional) A URL to online documentation that provides more information about the error.")
    private String referenceError;


}

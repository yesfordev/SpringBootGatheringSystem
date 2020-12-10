package com.wefunding.wdh.gs.login.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "true , false")
    private boolean success;

    @ApiModelProperty(value = "정상(0), 비정상")
    private int code;

    @ApiModelProperty(value = "message")
    private String msg;

}

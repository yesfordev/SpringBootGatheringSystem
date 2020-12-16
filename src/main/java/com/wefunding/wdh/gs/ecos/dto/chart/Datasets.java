package com.wefunding.wdh.gs.ecos.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by yes on 2020/12/16
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Datasets {
    private List<Double> data;
    private String label;
    private String borderColor;
    private boolean fill;
}

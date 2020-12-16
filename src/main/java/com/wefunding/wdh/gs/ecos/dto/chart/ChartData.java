package com.wefunding.wdh.gs.ecos.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Created by yes on 2020/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    private List<String> labels;
    private List<Datasets> datasets;
}

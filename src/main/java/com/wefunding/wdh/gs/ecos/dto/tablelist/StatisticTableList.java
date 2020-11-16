package com.wefunding.wdh.gs.ecos.dto.tablelist;

import lombok.Data;

import java.util.List;

/**
 * Created by yes on 2020/11/13
 */

@Data
public class StatisticTableList {

    private Integer list_total_count;

    private List<Row> row;

}

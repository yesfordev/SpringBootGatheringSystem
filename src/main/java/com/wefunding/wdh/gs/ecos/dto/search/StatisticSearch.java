package com.wefunding.wdh.gs.ecos.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class StatisticSearch {

    private Integer list_total_count;

    private List<Row> row;
}

package com.wefunding.wdh.gs.ecos.dto.itemlist;

import lombok.Data;

import java.util.List;

/**
 * Created by yes on 2020/11/16
 */
@Data
public class StatisticItemList {

    private Integer list_total_count;

    private List<Row> row;

}

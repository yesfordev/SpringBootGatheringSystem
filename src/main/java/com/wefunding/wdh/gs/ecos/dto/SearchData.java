package com.wefunding.wdh.gs.ecos.dto;

import lombok.Data;

@Data
public class SearchData {

    private String statName;

    private String itemCode1;

    private String itemName1;

    private String itemCode2;

    private String itemName2;

    private String itemCode3;

    private String itemName3;

    private String unitName;

    private Integer time;

    private String dataValue;

    public SearchData(String statName, String itemCode1, String itemName1, String itemCode2, String itemName2, String itemCode3, String itemName3, String unitName, Integer time, String dataValue) {
        this.statName = statName;
        this.itemCode1 = itemCode1;
        this.itemName1 = itemName1;
        this.itemCode2 = itemCode2;
        this.itemName2 = itemName2;
        this.itemCode3 = itemCode3;
        this.itemName3 = itemName3;
        this.unitName = unitName;
        this.time = time;
        this.dataValue = dataValue;
    }
}

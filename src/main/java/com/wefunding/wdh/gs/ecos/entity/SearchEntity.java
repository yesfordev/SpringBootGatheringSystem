package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "statistic_search_test", schema = "ecos")
public class SearchEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String STAT_CODE;

    private String STAT_NAME;

    private String ITEM_CODE1;

    private String ITEM_NAME1;

    private String ITEM_CODE2;

    private String ITEM_NAME2;

    private String ITEM_CODE3;

    private String ITEM_NAME3;

    private String UNIT_NAME;

    private String TIME;

    private String DATA_VALUE;
}

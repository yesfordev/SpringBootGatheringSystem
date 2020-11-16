package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yes on 2020/11/16
 */
@Entity
@Data
@Table(name = "statistic_item_list", schema = "ecos")
public class ItemListEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String statCode;

    private String statName;

    private String grpCode;

    private String grpName;

    private String pItemCode;

    private String itemCode;

    private String itemName;

    private String cycle;

    private String startTime;

    private String endTime;

    private String dataCnt;

}

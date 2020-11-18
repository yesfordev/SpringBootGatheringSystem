package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yes on 2020/11/16
 */

@Entity
@Data
@Table(name = "statistic_table_list_test", schema = "ecos")
public class TableListEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pStatCode;

    private String statCode;

    private String statName;

    private String cycle;

    private String srchYn;

    private String orgName;
}

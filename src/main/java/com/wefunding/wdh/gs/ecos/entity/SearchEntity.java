package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "statistic_search", schema = "ecos")
public class SearchEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String statCode;

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

    @CreationTimestamp
    private LocalDateTime regDt;

    @UpdateTimestamp
    private LocalDateTime updDt;

    private Integer masterId;

}

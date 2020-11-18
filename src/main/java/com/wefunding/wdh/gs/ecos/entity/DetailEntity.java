package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="detail", schema = "ecos")
public class DetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer detailId;

    private Integer masterId;

    private String itemListId;

    private String grpCode;

    private Integer flag;
}

package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="detail", schema = "ecos")
public class DetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer id;

    private Integer masterId;

    private Integer itemListId;

    private String grpCode;

    private String flag;
}

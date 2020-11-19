package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yes on 2020/11/19
 */
@Entity
@Data
@Table(name = "master", schema = "ecos")
public class MasterEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "master_id")
    private Integer id;

    private String statNameKr;

    private String statNameEn;

}

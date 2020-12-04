package com.wefunding.wdh.gs.ecos.entity;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    //0: 정상, 1: 오류, 2: 업데이트중
    private int status;

    private String cycle;

    @UpdateTimestamp
    private LocalDateTime recentUpdDt;

    private Integer flag;

    private String description;

}

package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailEntityRepository extends JpaRepository<DetailEntity, Integer> {

    @Query(value = "select item_list_id, grp_code, flag from ecos.detail where master_id <=(select count (*) from ecos.master)", nativeQuery = true)
    List<DetailEntity> getDetailTable();
}

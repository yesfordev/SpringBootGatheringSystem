package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.MasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by yes on 2020/11/19
 */
@Repository
public interface MasterEntityRepository extends JpaRepository<MasterEntity, Integer> {

    @Query(value = "SELECT master_id FROM ecos.master", nativeQuery = true)
    List<Integer> findIdAll();

    MasterEntity findById(int id);

    @Query(value = "select * from ecos.master m where master_id = :masterId", nativeQuery = true)
    List<MasterEntity> findByMasterId_MasterTable(@Param("masterId")Optional <Integer> masterId);

    //List<MasterEntity> findAllByMasterID(int id);
}

package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailEntityRepository extends JpaRepository<DetailEntity, Integer> {

    List<DetailEntity> findAllByMasterId(Integer masterId);
//    List<DetailEntity> findAllByMasterId111(Optional<Integer> masterId);

    @Query(value = "select * from ecos.detail d where master_id = :masterId", nativeQuery = true)
    List<DetailEntity> test555(@Param("masterId") Optional<Integer> masterId);
}

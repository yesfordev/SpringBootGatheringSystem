package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailEntityRepository extends JpaRepository<DetailEntity, Integer> {

    List<DetailEntity> findAllByMasterId(Integer masterId);

}

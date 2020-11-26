package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.MasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yes on 2020/11/19
 */
@Repository
public interface MasterEntityRepository extends JpaRepository<MasterEntity, Integer> {

    @Query(value = "SELECT master_id FROM ecos.master", nativeQuery = true)
    List<Integer> findIdAll();

    MasterEntity findById(int id);
}

package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.TableListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yes on 2020/11/16
 */
@Repository
public interface TableListEntityRepository extends JpaRepository<TableListEntity, Integer> {

}

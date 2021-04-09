package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import com.wefunding.wdh.gs.ecos.entity.ItemListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Created by yes on 2020/11/16
 */
@Repository
public interface ItemListEntityRepository extends JpaRepository<ItemListEntity, Integer> {

}

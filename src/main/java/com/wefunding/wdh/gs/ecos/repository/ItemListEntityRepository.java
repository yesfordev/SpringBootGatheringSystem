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

//    @Query(value = "SELECT item_code FROM ecos.statistic_item_list where id = :itemListId",nativeQuery = true)
//    List<String> findItemCodeById(@Param("itemListId") Integer itemListId);

//    @Query(value = "SELECT * FROM ecos.statistic_item_list where id = :itemListId",nativeQuery = true)
//    List<ItemListEntity> findAllById(@Param("itemListId") Integer itemListId);

//    Optional<ItemListEntity> findById(Integer itemListId);

    @Query(value = "select cast ((select time from ecos.statistic_search ss2 where time = :testaaa) as int)", nativeQuery = true)
    List<ItemListEntity> findnameaaa(@Param("testaaa") String testaaa);
}

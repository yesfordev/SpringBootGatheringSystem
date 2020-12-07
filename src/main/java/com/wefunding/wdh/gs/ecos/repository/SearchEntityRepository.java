package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.ItemListEntity;
import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchEntityRepository extends JpaRepository<SearchEntity, Integer> {

    SearchEntity findSearchEntityByMasterIdAndStatCodeAndItemCode1AndItemCode2AndItemCode3AndTime(int masterId, String statCode, String itemCode1, String itemCode2, String itemCode3, Integer time);

//    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and time >= :startTime and time <= :endTime", nativeQuery = true)
//    List<SearchEntity> findtest(@Param("masterId") Integer masterId,@Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
//
//    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and time >= :startTime and time <= :endTime", nativeQuery = true)
//    List<SearchEntity> findtest1(@Param("masterId") Integer masterId, @Param("itemCode1") String itemCode1, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
//
//    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and item_code2 = :itemCode2 and time >= :startTime and time <= :endTime", nativeQuery = true)
//    List<SearchEntity> findtest2(@Param("masterId") Integer masterId, @Param("itemCode1") String itemCode1, @Param("itemCode2") String itemCode2, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
//
//    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and item_code2 = :itemCode2 and item_code3 = :itemCode3 and time >= :startTime and time <= :endTime", nativeQuery = true)
//    List<SearchEntity> findtest3(@Param("masterId") Integer masterId, @Param("itemCode1") String itemCode1, @Param("itemCode2") String itemCode2, @Param("itemCode3") String itemCode3, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
//
//    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and item_code2 = :itemCode2 and item_code3 = :itemCode3 and time >= :startTime and time <= :endTime", nativeQuery = true)
//    List<SearchEntity> findtest4(@Param("masterId") Optional<Integer> masterId, @Param("itemCode1") Optional<String> itemCode1, @Param("itemCode2") Optional<String> itemCode2, @Param("itemCode3") Optional<String> itemCode3, @Param("startTime") Optional<Integer> startTime, @Param("endTime") Optional<Integer> endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_name1 in (:itemName1) and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> findByAreaMultiple(@Param("masterId") Integer masterId, @Param("itemName1") Optional<List> itemName1, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_name1 = :itemName1 and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> findByArea(@Param("masterId") Integer masterId, @Param("itemName1") String itemName1, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> searchTableInformation(@Param("masterId") Optional<Integer> masterId, @Param("startTime") Optional<Integer> startTime, @Param("endTime") Optional<Integer> endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> itemCode1Information(@Param("masterId") Optional<Integer> masterId, @Param("itemCode1") Optional<String> itemCode1, @Param("startTime") Optional<Integer> startTime, @Param("endTime") Optional<Integer> endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and item_code2 = :itemCode2 and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> itemCode2Information(@Param("masterId") Optional<Integer> masterId, @Param("itemCode1") Optional<String> itemCode1, @Param("itemCode2") Optional<String> itemCode2, @Param("startTime") Optional<Integer> startTime, @Param("endTime") Optional<Integer> endTime);

    @Query(value = "select * from ecos.statistic_search_test where master_id = :masterId and item_code1 = :itemCode1 and item_code2 = :itemCode2 and item_code3 = :itemCode3 and time >= :startTime and time <= :endTime", nativeQuery = true)
    List<SearchEntity> itemCode3Information(@Param("masterId") Optional<Integer> masterId, @Param("itemCode1") Optional<String> itemCode1, @Param("itemCode2") Optional<String> itemCode2, @Param("itemCode3") Optional<String> itemCode3, @Param("startTime") Optional<Integer> startTime, @Param("endTime") Optional<Integer> endTime);

}

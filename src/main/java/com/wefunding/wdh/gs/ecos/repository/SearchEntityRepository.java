package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchEntityRepository extends JpaRepository<SearchEntity, Integer> {

    SearchEntity findSearchEntityByMasterIdAndStatCodeAndItemCode1AndItemCode2AndItemCode3AndTime(int masterId, String statCode, String itemCode1, String itemCode2, String itemCode3, String time);
}

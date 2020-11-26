package com.wefunding.wdh.gs.ecos.repository;

import com.wefunding.wdh.gs.ecos.entity.SearchHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryEntityRepository extends JpaRepository<SearchHistoryEntity, Integer> {

}

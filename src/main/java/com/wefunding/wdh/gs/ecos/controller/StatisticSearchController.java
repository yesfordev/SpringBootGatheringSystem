package com.wefunding.wdh.gs.ecos.controller;


import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import com.wefunding.wdh.gs.ecos.repository.DetailEntityRepository;
import com.wefunding.wdh.gs.ecos.utils.EcosUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("search")
@RequiredArgsConstructor
public class StatisticSearchController {

    private final DetailEntityRepository detailEntityRepository;

    @Value("${serviceKey}")
    private String serviceKey;

    @GetMapping("/saveAll")
    public void saveSearch() throws IOException{

        EcosUtils ecosUtils = new EcosUtils();

        List<DetailEntity> detailEntityList = detailEntityRepository.getDetailTable();

        //System.out.println(detailEntityList);

        for(DetailEntity detailEntity : detailEntityList){
            System.out.println(detailEntity.getItemListId());
        }
    }
}

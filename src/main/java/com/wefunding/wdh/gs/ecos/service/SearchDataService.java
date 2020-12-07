//package com.wefunding.wdh.gs.ecos.service;
//
//import com.wefunding.wdh.gs.ecos.dto.SearchData;
//import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
//import com.wefunding.wdh.gs.ecos.repository.DetailEntityRepository;
//import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class SearchDataService {
//
//    private static SearchEntityRepository searchEntityRepository;
//
//    @Autowired
//    DetailEntityRepository detailEntityRepository;
//
//    public static Object listDataSearch(Optional<Integer> masterId, Optional<Integer> startTime, Optional<Integer> endTime, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3){
//
//        List<SearchData> searchDataList = new ArrayList<>();
//        try {
//            List<SearchEntity> searchEntityList = searchEntityRepository.itemCode3Information(masterId, itemCode1, itemCode2, itemCode3, startTime, endTime);
//            for (SearchEntity searchEntity : searchEntityList) {
//                searchDataList.add(new SearchData(searchEntity.getStatName(),
//                        searchEntity.getItemCode1(),
//                        searchEntity.getItemName1(),
//                        searchEntity.getItemCode2(),
//                        searchEntity.getItemName2(),
//                        searchEntity.getItemCode3(),
//                        searchEntity.getItemName3(),
//                        searchEntity.getUnitName(),
//                        searchEntity.getTime(),
//                        searchEntity.getDataValue()));
//            }
//
//            return searchDataList;
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return HttpStatus.BAD_REQUEST;
//    }
//
//
//}

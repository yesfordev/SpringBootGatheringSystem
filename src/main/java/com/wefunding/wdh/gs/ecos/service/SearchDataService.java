package com.wefunding.wdh.gs.ecos.service;

import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import org.json.CDL;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class SearchDataService {
    @Autowired
    SearchEntityRepository searchEntityRepository;

    public String searchTableSaveDataCSV(Optional<Integer> masterId, Optional<Integer> startTime, Optional<Integer> endTime) {

        JSONArray jsonArr_result = new JSONArray();

        List<SearchEntity> searchEntityList = searchEntityRepository.searchTableInformation(masterId, startTime, endTime);
        for (SearchEntity searchEntity : searchEntityList) {
            JSONObject result = new JSONObject();
            result.put("statName", searchEntity.getStatName());
            result.put("ItemCode1", searchEntity.getItemCode1());
            result.put("ItemName1", searchEntity.getItemName1());
            result.put("ItemCode2", searchEntity.getItemCode2());
            result.put("ItemName2", searchEntity.getItemName2());
            result.put("ItemCode3", searchEntity.getItemCode3());
            result.put("ItemName3", searchEntity.getItemName3());
            result.put("UnitName", searchEntity.getUnitName());
            result.put("Time", searchEntity.getTime());
            result.put("DataValue", searchEntity.getDataValue());
            jsonArr_result.put(result);
        }
        String csv = CDL.toString(jsonArr_result);
        return csv;
    }
    public String itemCode1SaveDataCSV(Optional<Integer> masterId, Optional<String> itemCode1, Optional<Integer> startTime, Optional<Integer> endTime) {

        JSONArray jsonArr_result = new JSONArray();

        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode1Information(masterId,itemCode1, startTime, endTime);
        for (SearchEntity searchEntity : searchEntityList) {
            JSONObject result = new JSONObject();
            result.put("statName", searchEntity.getStatName());
            result.put("ItemCode1", searchEntity.getItemCode1());
            result.put("ItemName1", searchEntity.getItemName1());
            result.put("ItemCode2", searchEntity.getItemCode2());
            result.put("ItemName2", searchEntity.getItemName2());
            result.put("ItemCode3", searchEntity.getItemCode3());
            result.put("ItemName3", searchEntity.getItemName3());
            result.put("UnitName", searchEntity.getUnitName());
            result.put("Time", searchEntity.getTime());
            result.put("DataValue", searchEntity.getDataValue());
            jsonArr_result.put(result);
        }
        String csv = CDL.toString(jsonArr_result);
        return csv;
    }
    public String itemCode2SaveDataCSV(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<Integer> startTime, Optional<Integer> endTime) {

        JSONArray jsonArr_result = new JSONArray();

        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode2Information(masterId, itemCode1, itemCode2,startTime, endTime);
        for (SearchEntity searchEntity : searchEntityList) {
            JSONObject result = new JSONObject();
            result.put("statName", searchEntity.getStatName());
            result.put("ItemCode1", searchEntity.getItemCode1());
            result.put("ItemName1", searchEntity.getItemName1());
            result.put("ItemCode2", searchEntity.getItemCode2());
            result.put("ItemName2", searchEntity.getItemName2());
            result.put("ItemCode3", searchEntity.getItemCode3());
            result.put("ItemName3", searchEntity.getItemName3());
            result.put("UnitName", searchEntity.getUnitName());
            result.put("Time", searchEntity.getTime());
            result.put("DataValue", searchEntity.getDataValue());
            jsonArr_result.put(result);
        }
        String csv = CDL.toString(jsonArr_result);
        return csv;
    }
    public String itemCode3SaveDataCSV(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3, Optional<Integer> startTime, Optional<Integer> endTime) {

        JSONArray jsonArr_result = new JSONArray();

        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode3Information(masterId, itemCode1, itemCode2, itemCode3,startTime, endTime);
        for (SearchEntity searchEntity : searchEntityList) {
            JSONObject result = new JSONObject();
            result.put("statName", searchEntity.getStatName());
            result.put("ItemCode1", searchEntity.getItemCode1());
            result.put("ItemName1", searchEntity.getItemName1());
            result.put("ItemCode2", searchEntity.getItemCode2());
            result.put("ItemName2", searchEntity.getItemName2());
            result.put("ItemCode3", searchEntity.getItemCode3());
            result.put("ItemName3", searchEntity.getItemName3());
            result.put("UnitName", searchEntity.getUnitName());
            result.put("Time", searchEntity.getTime());
            result.put("DataValue", searchEntity.getDataValue());
            jsonArr_result.put(result);
        }
        String csv = CDL.toString(jsonArr_result);
        return csv;
    }

//    public ResponseEntity searchTableSaveDataJSON(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3, Optional<Integer> startTime, Optional<Integer> endTime) {
//
//        JSONArray jsonArr_result = new JSONArray();
//
//        List<SearchEntity> searchEntityList = searchEntityRepository.searchTableInformation(masterId, startTime, endTime);
//        for (SearchEntity searchEntity : searchEntityList) {
//            JSONObject result = new JSONObject();
//            result.put("statName", searchEntity.getStatName());
//            result.put("ItemCode1", searchEntity.getItemCode1());
//            result.put("ItemName1", searchEntity.getItemName1());
//            result.put("ItemCode2", searchEntity.getItemCode2());
//            result.put("ItemName2", searchEntity.getItemName2());
//            result.put("ItemCode3", searchEntity.getItemCode3());
//            result.put("ItemName3", searchEntity.getItemName3());
//            result.put("UnitName", searchEntity.getUnitName());
//            result.put("Time", searchEntity.getTime());
//            result.put("DataValue", searchEntity.getDataValue());
//            jsonArr_result.put(result);
//        }
//        return new ResponseEntity(jsonArr_result, HttpStatus.OK);
//    }
//    public ResponseEntity itemCode1SaveDataJSON(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3, Optional<Integer> startTime, Optional<Integer> endTime) {
//
//        JSONArray jsonArr_result = new JSONArray();
//
//        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode1Information(masterId,itemCode1, startTime, endTime);
//        for (SearchEntity searchEntity : searchEntityList) {
//            JSONObject result = new JSONObject();
//            result.put("statName", searchEntity.getStatName());
//            result.put("ItemCode1", searchEntity.getItemCode1());
//            result.put("ItemName1", searchEntity.getItemName1());
//            result.put("ItemCode2", searchEntity.getItemCode2());
//            result.put("ItemName2", searchEntity.getItemName2());
//            result.put("ItemCode3", searchEntity.getItemCode3());
//            result.put("ItemName3", searchEntity.getItemName3());
//            result.put("UnitName", searchEntity.getUnitName());
//            result.put("Time", searchEntity.getTime());
//            result.put("DataValue", searchEntity.getDataValue());
//            jsonArr_result.put(result);
//        }
//        return new ResponseEntity(jsonArr_result, HttpStatus.OK);
//    }
//    public ResponseEntity itemCode2SaveDataJSON(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3, Optional<Integer> startTime, Optional<Integer> endTime) {
//
//        JSONArray jsonArr_result = new JSONArray();
//
//        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode2Information(masterId, itemCode1, itemCode2,startTime, endTime);
//        for (SearchEntity searchEntity : searchEntityList) {
//            JSONObject result = new JSONObject();
//            result.put("statName", searchEntity.getStatName());
//            result.put("ItemCode1", searchEntity.getItemCode1());
//            result.put("ItemName1", searchEntity.getItemName1());
//            result.put("ItemCode2", searchEntity.getItemCode2());
//            result.put("ItemName2", searchEntity.getItemName2());
//            result.put("ItemCode3", searchEntity.getItemCode3());
//            result.put("ItemName3", searchEntity.getItemName3());
//            result.put("UnitName", searchEntity.getUnitName());
//            result.put("Time", searchEntity.getTime());
//            result.put("DataValue", searchEntity.getDataValue());
//            jsonArr_result.put(result);
//        }
//        return new ResponseEntity(jsonArr_result, HttpStatus.OK);
//    }
//    public ResponseEntity itemCode3SaveDataJSON(Optional<Integer> masterId, Optional<String> itemCode1, Optional<String> itemCode2, Optional<String> itemCode3, Optional<Integer> startTime, Optional<Integer> endTime) {
//
//        JSONArray jsonArr_result = new JSONArray();
//
//        List<SearchEntity> searchEntityList = searchEntityRepository.itemCode3Information(masterId, itemCode1, itemCode2, itemCode3,startTime, endTime);
//        for (SearchEntity searchEntity : searchEntityList) {
//            JSONObject result = new JSONObject();
//            result.put("statName", searchEntity.getStatName());
//            result.put("ItemCode1", searchEntity.getItemCode1());
//            result.put("ItemName1", searchEntity.getItemName1());
//            result.put("ItemCode2", searchEntity.getItemCode2());
//            result.put("ItemName2", searchEntity.getItemName2());
//            result.put("ItemCode3", searchEntity.getItemCode3());
//            result.put("ItemName3", searchEntity.getItemName3());
//            result.put("UnitName", searchEntity.getUnitName());
//            result.put("Time", searchEntity.getTime());
//            result.put("DataValue", searchEntity.getDataValue());
//            jsonArr_result.put(result);
//        }
//        return new ResponseEntity(jsonArr_result, HttpStatus.OK);
//    }
}
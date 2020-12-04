package com.wefunding.wdh.gs.ecos.controller;

import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import com.wefunding.wdh.gs.ecos.entity.ItemListEntity;
import com.wefunding.wdh.gs.ecos.entity.MasterEntity;
import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import com.wefunding.wdh.gs.ecos.repository.DetailEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.ItemListEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.MasterEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fronttest")
@RequiredArgsConstructor
public class FrontTestController {

    private final MasterEntityRepository masterEntityRepository;

    private final SearchEntityRepository searchEntityRepository;

    private final DetailEntityRepository detailEntityRepository;

    private final ItemListEntityRepository itemListEntityRepository;
//
//    @GetMapping("")
//    public ResponseEntity getTest(@PathVariable(value = "masterId") int masterId){
//        try{
//            List<SearchEntity> searchEntityList = searchEntityRepository.finditemcode(masterId);
//            return new ResponseEntity(searchEntityList, HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("test/{startTime}/{endTime}")
//    public ResponseEntity getTest(@PathVariable(value = "masterId") int masterId, @PathVariable(value = "startTime") int startTime, @PathVariable(value = "endTime") int endTime) {
//        List<SearchEntity> searchEntityList = searchEntityRepository.findtest(masterId,startTime,endTime);
//        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
//    }

    @GetMapping("/statisticSearch")
    public ResponseEntity<?> getTest(@RequestParam(value = "masterId") int masterId, @RequestParam(value = "startTime") int startTime, @RequestParam(value = "endTime") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest(masterId,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("/statisticSearch/one")
    public ResponseEntity<?> getTest1(@RequestParam(value = "masterId") int masterId, @RequestParam(value = "itemCode1") String itemCode1, @RequestParam(value = "startTime") int startTime, @RequestParam(value = "endTime") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest1(masterId,itemCode1,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("/statisticSearch/area")
    public ResponseEntity<?> getStatisticSearchByArea(@RequestParam(value = "masterId") int masterId, @RequestParam(value = "itemName1") String itemName1, @RequestParam(value = "startTime") int startTime, @RequestParam(value = "endTime") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findByArea(masterId,itemName1,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("/statisticSearch/two")
    public ResponseEntity<?> getTest2(@RequestParam(value = "masterId") int masterId, @RequestParam(value = "itemCode1") String itemCode1, @RequestParam(value = "itemCode2") String itemCode2, @RequestParam(value = "startTime") int startTime, @RequestParam(value = "endTime") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest2(masterId,itemCode1,itemCode2,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("/statisticSearch/three")
    public ResponseEntity<?> getTest3(@RequestParam(value = "masterId") int masterId, @RequestParam(value = "itemCode1") String itemCode1, @RequestParam(value = "itemCode2") String itemCode2, @RequestParam(value = "itemCode3") String itemCode3, @RequestParam(value = "startTime") int startTime, @RequestParam(value = "endTime") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest3(masterId,itemCode1,itemCode2,itemCode3,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    /**
     *
     * @param masterId
     * @return
     */
    @ApiOperation(value = "isArea,cycle,area 출력", notes="msg = master_id")
    @GetMapping("masterDetailResult")
    public ResponseEntity<?> getMasterDetailResult(@RequestParam(value = "masterId") int masterId) {
        MasterEntity masterIdList = masterEntityRepository.findById(masterId);
        JSONArray jsonArr_result = new JSONArray();
        String cycle = null;
        if(masterIdList.getFlag().equals(1))
        {
            List<DetailEntity> detailEntityList = detailEntityRepository.findAllByMasterId(masterId);
            List<String> area = new ArrayList<>();
            for(DetailEntity detailEntity : detailEntityList){
                Optional<ItemListEntity> itemListEntityTemp = itemListEntityRepository.findById(detailEntity.getItemListId());
                cycle = itemListEntityTemp.get().getCycle();
                area.add(itemListEntityTemp.get().getItemName());
            }
            JSONObject result = new JSONObject();
            String flag = "true";
            result.put("isArea",flag);
            result.put("cycle",cycle);
            result.put("area",area);
            jsonArr_result.add(result);
        }
        else
        {
            List<DetailEntity> detailEntityList = detailEntityRepository.findAllByMasterId(masterId);
            List<String> area = new ArrayList<>();
            Optional<ItemListEntity> itemListEntityTemp = itemListEntityRepository.findById(detailEntityList.get(0).getItemListId());
            cycle = itemListEntityTemp.get().getCycle();

            JSONObject result = new JSONObject();
            String flag = "false";
            result.put("isArea",flag);
            result.put("cycle",cycle);
            result.put("area",area);
            jsonArr_result.add(result);
        }

        ArrayList<JSONObject> arrayJson = new ArrayList<>();

        for (int k = 0; k < jsonArr_result.size(); k++) {
            JSONObject tempJson = (JSONObject) jsonArr_result.get(k);
            arrayJson.add(tempJson);
        }

        JSONObject[] jsons = new JSONObject[arrayJson.size()];
        arrayJson.toArray(jsons);

        return new ResponseEntity<>(jsons, HttpStatus.OK);
    }
}

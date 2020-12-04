package com.wefunding.wdh.gs.ecos.controller;

import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("front")
@RequiredArgsConstructor
public class FrontTestController {

    private final SearchEntityRepository searchEntityRepository;


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
}

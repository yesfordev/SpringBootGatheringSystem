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
@RequestMapping("fronttest")
@RequiredArgsConstructor
public class FrontTestController {

    private final SearchEntityRepository searchEntityRepository;


    @GetMapping("test")
    public ResponseEntity<?> getTest(@RequestParam(value = "msg") int masterId, @RequestParam(value = "msg2") int startTime, @RequestParam(value = "msg3") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest(masterId,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("test1")
    public ResponseEntity<?> getTest1(@RequestParam(value = "msg") int masterId, @RequestParam(value = "msg2") String itemCode1, @RequestParam(value = "msg3") int startTime, @RequestParam(value = "msg4") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest1(masterId,itemCode1,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("test2")
    public ResponseEntity<?> getTest2(@RequestParam(value = "msg") int masterId, @RequestParam(value = "msg2") String itemCode1, @RequestParam(value = "msg3") String itemCode2, @RequestParam(value = "msg4") int startTime, @RequestParam(value = "msg5") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest2(masterId,itemCode1,itemCode2,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }

    @GetMapping("test3")
    public ResponseEntity<?> getTest3(@RequestParam(value = "msg") int masterId, @RequestParam(value = "msg2") String itemCode1, @RequestParam(value = "msg3") String itemCode2, @RequestParam(value = "msg4") String itemCode3, @RequestParam(value = "msg5") int startTime, @RequestParam(value = "msg6") int endTime) {
        List<SearchEntity> searchEntityList = searchEntityRepository.findtest3(masterId,itemCode1,itemCode2,itemCode3,startTime,endTime);
        return new ResponseEntity<>(searchEntityList, HttpStatus.OK);
    }
}

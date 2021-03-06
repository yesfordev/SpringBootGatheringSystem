package com.wefunding.wdh.gs.ecos.controller;

import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import com.wefunding.wdh.gs.ecos.service.SearchDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SearchData")
public class SearchDataController {

    @Autowired
    SearchEntityRepository searchEntityRepository;

    @Autowired
    SearchDataService searchDataService;

    @ApiOperation(value = "CSV download(CP949 encoding)", notes = "통합")
    @GetMapping("/export-csv")
    public ResponseEntity exportCSV(HttpServletResponse response, @RequestParam(value = "masterId") Optional<Integer> masterId, @RequestParam(value = "itemName1") Optional<List<String>> itemName1, @RequestParam(value = "itemCode1") Optional<String> itemCode1, @RequestParam(value = "itemCode2") Optional<String> itemCode2, @RequestParam(value = "itemCode3") Optional<String> itemCode3, @RequestParam(value = "startTime") Optional<Integer> startTime, @RequestParam(value = "endTime") Optional<Integer> endTime) throws UnsupportedEncodingException {

        String filename = "SearchData.csv";
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", filename);
        response.setHeader(headerKey, headerValue);

        try {
            if (itemName1.isEmpty()&itemCode1.isEmpty()&itemCode2.isEmpty()&itemCode3.isEmpty()) {
                return new ResponseEntity(searchDataService.searchTableDataCSV(masterId, startTime, endTime).getBytes("CP949"), HttpStatus.OK);
            } else if (itemName1.isPresent()&itemCode1.isEmpty()&itemCode2.isEmpty()&itemCode3.isEmpty()) {
                return new ResponseEntity(searchDataService.multiAreaDataCSV(masterId, itemName1, startTime, endTime).getBytes("CP949"), HttpStatus.OK);
            } else if (itemName1.isEmpty()&itemCode1.isPresent()&itemCode2.isEmpty()&itemCode3.isEmpty()) {
                return new ResponseEntity(searchDataService.itemCode1SaveDataCSV(masterId, itemCode1, startTime, endTime).getBytes("CP949"), HttpStatus.OK);
            } else if (itemName1.isEmpty()&itemCode1.isPresent()&itemCode2.isPresent()&itemCode3.isEmpty()){
                return new ResponseEntity(searchDataService.itemCode2SaveDataCSV(masterId, itemCode1, itemCode2, startTime, endTime).getBytes("CP949"), HttpStatus.OK);
            } else if (itemName1.isEmpty()&itemCode1.isPresent()&itemCode2.isPresent()&itemCode3.isPresent()){
                return new ResponseEntity(searchDataService.itemCode3SaveDataCSV(masterId, itemCode1, itemCode2, itemCode3, startTime, endTime).getBytes("CP949"), HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
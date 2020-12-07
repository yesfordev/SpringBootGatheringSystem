//package com.wefunding.wdh.gs.ecos.controller;
//
//import com.google.common.net.HttpHeaders;
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;
//import com.wefunding.wdh.gs.ecos.dto.SearchData;
//import com.wefunding.wdh.gs.ecos.service.SearchDataService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Optional;
//
//@Controller
//public class SearchDataController {
//
//    private SearchDataService searchDataService;
//
//    public SearchDataController(SearchDataService searchDataService){
//        this.searchDataService = searchDataService;
//    }
//
//    @GetMapping("/export-searchData/{masterId}/{startTime}/{endTime}/{itemCode1}/{itemCode2}/{itemCode3}")
//    public void exportCSV(@RequestParam(value = "masterId") Optional<Integer> masterId, @RequestParam(value = "itemCode1") Optional<String> itemCode1, @RequestParam(value = "itemCode2") Optional<String> itemCode2, @RequestParam(value = "itemCode3") Optional<String> itemCode3, @RequestParam(value = "startTime") Optional<Integer> startTime, @RequestParam(value = "endTime") Optional<Integer> endTime, HttpServletResponse response) throws Exception{
//
//        String filename = "SearchData.csv";
//
//        response.setContentType("text/csv");
//        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename +"\"");
//
//        //create a csv writer
//        StatefulBeanToCsv<SearchData> writer = new StatefulBeanToCsvBuilder<SearchData>(response.getWriter())
//                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withOrderedResults(false)
//                .build();
//
//        //write all users to csv file
//        writer.write((SearchData) SearchDataService.listDataSearch(masterId,startTime,endTime,itemCode1,itemCode2,itemCode3));
//    }
//}

package com.wefunding.wdh.gs.ecos.controller;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.wefunding.wdh.gs.ecos.dto.search.Row;
import com.wefunding.wdh.gs.ecos.dto.search.SearchRes;
import com.wefunding.wdh.gs.ecos.entity.DetailEntity;
import com.wefunding.wdh.gs.ecos.entity.ItemListEntity;
import com.wefunding.wdh.gs.ecos.entity.MasterEntity;
import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import com.wefunding.wdh.gs.ecos.repository.DetailEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.ItemListEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.MasterEntityRepository;
import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import com.wefunding.wdh.gs.ecos.service.StatisticSearchService;
import com.wefunding.wdh.gs.ecos.utils.EcosUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("search")
@RequiredArgsConstructor
public class StatisticSearchController {

    private final DetailEntityRepository detailEntityRepository;

    private final MasterEntityRepository masterEntityRepository;

    private final SearchEntityRepository searchEntityRepository;

    private final ItemListEntityRepository itemListEntityRepository;

    @Autowired
    StatisticSearchService statisticSearchService;

    @Value("${serviceKey}")
    private String serviceKey;


    /**
     * 특정 통계 조회[갱신]
     */
    @RequestMapping(value = "/stat/update/{masterId}")
    public String updateSearch(@PathVariable("masterId") int masterId) throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        statisticSearchService.saveSearch(masterId);

        String returnMsg = "";

        return returnMsg;
    }


    /**
     * 통계 조회 조건 설정 저장
     * @return save 완료 문구
     * @throws IOException
     */
    @ApiOperation(value = "saveall", notes="9개 지표 data save")
    @GetMapping("/saveAll")
    public String saveSearch() throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        List<Integer> masterIdList = masterEntityRepository.findIdAll();

//        log.info("masterIdList: "+masterIdList);

        for (Integer masterId : masterIdList) {

            List<DetailEntity> detailEntityList = detailEntityRepository.findAllByMasterId(masterId);

            String flag = detailEntityList.get(0).getFlag();

//            List<String> itemCodeList = itemListEntityRepository.findItemCodeById(detailEntityList.get(0).getItemListId());

//            log.info(String.valueOf(itemCodeList));

            if (flag.equals("1")) {
                for (DetailEntity detailEntityTemp : detailEntityList) {
                    Optional<ItemListEntity> itemListEntityTemp = itemListEntityRepository.findById(detailEntityTemp.getItemListId());

                    int startCount = 1;
                    int finishCount = 2000;

                    while (true) {

                        String urlstr = "http://ecos.bok.or.kr/api/StatisticSearch/" +
                                serviceKey +
                                "/json/kr/" +
                                +startCount + "/"
                                + finishCount + "/"
                                + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStatCode() + "/"
                                + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getCycle() + "/"
                                + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStartTime() + "/"
                                + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getEndTime() + "/"
                                + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();

                        JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

//                            log.info("RETURN ORIGIN" + resObject);

                        ObjectMapper mapper = new ObjectMapper();

                        //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);


                        try {
                            SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);

                            List<Row> rowList = searchRes.getStatisticSearch().getRow();
                            List<SearchEntity> searchEntityList = new ArrayList<>();

                            for (Row row : rowList) {
//                                    ItemListEntity itemListEntityTemp = new ItemListEntity();
                                SearchEntity searchEntityTemp = new SearchEntity();

                                searchEntityTemp.setStatCode(row.getSTAT_CODE());
                                searchEntityTemp.setStatName(row.getSTAT_NAME());
                                searchEntityTemp.setItemCode1(row.getITEM_CODE1());
                                searchEntityTemp.setItemName1(row.getITEM_NAME1());
                                searchEntityTemp.setItemCode2(row.getITEM_CODE2());
                                searchEntityTemp.setItemName2(row.getITEM_NAME2());
                                searchEntityTemp.setItemCode3(row.getITEM_CODE3());
                                searchEntityTemp.setItemName3(row.getITEM_NAME3());
                                searchEntityTemp.setUnitName(row.getUNIT_NAME());
                                searchEntityTemp.setTime(row.getTIME());
                                searchEntityTemp.setDataValue(row.getDATA_VALUE());
                                searchEntityTemp.setMasterId(masterId);

                                searchEntityList.add(searchEntityTemp);

                            }

                            searchEntityRepository.saveAll(searchEntityList);

//                log.info("id: " + detailEntityTemp.getId());
//                log.info("list_total_count: " + searchRes.getStatisticSearch().getList_total_count());
//                log.info("총 아이템: " + searchRes.getStatisticSearch().getRow());
                        } catch (UnrecognizedPropertyException e) {
                            String resString = resObject.toString();

                            int start = resString.indexOf("CODE\":");
                            int end = resString.indexOf("\"}}");

                            String errCode = resString.substring(start + "CODE\":".length(), end);

                            if (errCode.equals("500")) {
                                log.info("한국은행 API 서버 내부 에러");
                                return "서버 에러로 인하여 detail id - " + detailEntityTemp.getId() + " 에서 종료되었습니다.";
                            }
                            log.info("master id: " + detailEntityTemp.getId() + " 에서 데이터 없음");
                        }


                        if (finishCount < Integer.parseInt(itemListEntityTemp.orElseThrow(NoSuchElementException::new).getDataCnt())) {
                            startCount += 2000;
                            finishCount += 2000;
                        } else {
                            break;
                        }
                    }
                    log.info("master Id: " + masterId + "저장 완료");
                }
            } else if (flag.equals("2")) {

                String GroupOne = "";
                String GroupTwo = "";
                Optional<ItemListEntity> itemListEntityTemp = null;

                for (DetailEntity detailEntityTemp : detailEntityList) {
                    itemListEntityTemp = itemListEntityRepository.findById(detailEntityTemp.getItemListId());

                    if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group1")) {
                        GroupOne = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                    } else if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group2")) {
                        GroupTwo = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                    } else {
                        log.info("Database flag is wrong");
                        return "save failed";
                    }
                }

                int startCount = 1;
                int finishCount = 2000;

                while (true) {
                    String urlstr = "http://ecos.bok.or.kr/api/StatisticSearch/" +
                            serviceKey +
                            "/json/kr/" +
                            +startCount + "/"
                            + finishCount + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStatCode() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getCycle() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStartTime() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getEndTime() + "/"
                            + GroupOne + "/"
                            + GroupTwo;

                    JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

                    log.info("url: " + urlstr);
                    log.info("RETURN ORIGIN" + resObject);

                    ObjectMapper mapper = new ObjectMapper();

                    //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);


                    try {
                        SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);

                        List<Row> rowList = searchRes.getStatisticSearch().getRow();
                        List<SearchEntity> searchEntityList = new ArrayList<>();

                        for (Row row : rowList) {
//                                    ItemListEntity itemListEntityTemp = new ItemListEntity();
                            SearchEntity searchEntityTemp = new SearchEntity();

                            searchEntityTemp.setStatCode(row.getSTAT_CODE());
                            searchEntityTemp.setStatName(row.getSTAT_NAME());
                            searchEntityTemp.setItemCode1(row.getITEM_CODE1());
                            searchEntityTemp.setItemName1(row.getITEM_NAME1());
                            searchEntityTemp.setItemCode2(row.getITEM_CODE2());
                            searchEntityTemp.setItemName2(row.getITEM_NAME2());
                            searchEntityTemp.setItemCode3(row.getITEM_CODE3());
                            searchEntityTemp.setItemName3(row.getITEM_NAME3());
                            searchEntityTemp.setUnitName(row.getUNIT_NAME());
                            searchEntityTemp.setTime(row.getTIME());
                            searchEntityTemp.setDataValue(row.getDATA_VALUE());
                            searchEntityTemp.setMasterId(masterId);

                            searchEntityList.add(searchEntityTemp);

                        }

                        searchEntityRepository.saveAll(searchEntityList);

//                log.info("id: " + detailEntityTemp.getId());
//                log.info("list_total_count: " + searchRes.getStatisticSearch().getList_total_count());
//                log.info("총 아이템: " + searchRes.getStatisticSearch().getRow());
                    } catch (UnrecognizedPropertyException e) {
                        String resString = resObject.toString();

                        int start = resString.indexOf("CODE\":");
                        int end = resString.indexOf("\"}}");

                        String errCode = resString.substring(start + "CODE\":".length(), end);

                        if (errCode.equals("500")) {
                            log.info("한국은행 API 서버 내부 에러");
                            return "서버 에러로 인하여 master id - " + masterId + " 에서 종료되었습니다.";
                        }
                        log.info("master id: " + masterId + " 에서 데이터 없음");
                    }

                    if (finishCount < Integer.parseInt(itemListEntityTemp.orElseThrow(NoSuchElementException::new).getDataCnt())) {
                        startCount += 2000;
                        finishCount += 2000;
                    } else {
                        break;
                    }
                }
                log.info("master Id: " + masterId + "저장 완료");

            } else if (flag.equals("3")) {

                String GroupOne = "";
                String GroupTwo = "";
                String GroupThree = "";
                Optional<ItemListEntity> itemListEntityTemp = null;

                for (DetailEntity detailEntityTemp : detailEntityList) {
                    itemListEntityTemp = itemListEntityRepository.findById(detailEntityTemp.getItemListId());

                    if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group1")) {
                        GroupOne = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                    } else if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group2")) {
                        GroupTwo = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                    } else if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group3")) {
                        GroupThree = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                    } else {
                        log.info("Database flag is wrong");
                        return "save failed";
                    }
                }

                int startCount = 1;
                int finishCount = 2000;

                while (true) {
                    String urlstr = "http://ecos.bok.or.kr/api/StatisticSearch/" +
                            serviceKey +
                            "/json/kr/" +
                            +startCount + "/"
                            + finishCount + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStatCode() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getCycle() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStartTime() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getEndTime() + "/"
                            + GroupOne + "/"
                            + GroupTwo + "/"
                            + GroupThree;

                    JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

                    log.info("url: " + urlstr);
                    log.info("RETURN ORIGIN" + resObject);

                    ObjectMapper mapper = new ObjectMapper();

                    //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);


                    try {
                        SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);

                        List<Row> rowList = searchRes.getStatisticSearch().getRow();
                        List<SearchEntity> searchEntityList = new ArrayList<>();

                        for (Row row : rowList) {
//                                    ItemListEntity itemListEntityTemp = new ItemListEntity();
                            SearchEntity searchEntityTemp = new SearchEntity();

                            searchEntityTemp.setStatCode(row.getSTAT_CODE());
                            searchEntityTemp.setStatName(row.getSTAT_NAME());
                            searchEntityTemp.setItemCode1(row.getITEM_CODE1());
                            searchEntityTemp.setItemName1(row.getITEM_NAME1());
                            searchEntityTemp.setItemCode2(row.getITEM_CODE2());
                            searchEntityTemp.setItemName2(row.getITEM_NAME2());
                            searchEntityTemp.setItemCode3(row.getITEM_CODE3());
                            searchEntityTemp.setItemName3(row.getITEM_NAME3());
                            searchEntityTemp.setUnitName(row.getUNIT_NAME());
                            searchEntityTemp.setTime(row.getTIME());
                            searchEntityTemp.setDataValue(row.getDATA_VALUE());
                            searchEntityTemp.setMasterId(masterId);

                            searchEntityList.add(searchEntityTemp);

                        }

                        searchEntityRepository.saveAll(searchEntityList);

//                log.info("id: " + detailEntityTemp.getId());
//                log.info("list_total_count: " + searchRes.getStatisticSearch().getList_total_count());
//                log.info("총 아이템: " + searchRes.getStatisticSearch().getRow());
                    } catch (UnrecognizedPropertyException e) {
                        String resString = resObject.toString();

                        int start = resString.indexOf("CODE\":");
                        int end = resString.indexOf("\"}}");

                        String errCode = resString.substring(start + "CODE\":".length(), end);

                        if (errCode.equals("500")) {
                            log.info("한국은행 API 서버 내부 에러");
                            return "서버 에러로 인하여 master id - " + masterId + " 에서 종료되었습니다.";
                        }
                        log.info("master id: " + masterId + " 에서 데이터 없음");
                    }

                    if (finishCount < Integer.parseInt(itemListEntityTemp.orElseThrow().getDataCnt())) {
                        startCount += 2000;
                        finishCount += 2000;
                    } else {
                        break;
                    }
                }
                log.info("master Id: " + masterId + "저장 완료");

            } else {
                return "masterId " + masterId + "의 flag 값이 잘못 되었습니다. flag:" + flag;
            }
        }
        return "test finished";
    }




    @GetMapping("test1")
    public ResponseEntity<?> getTest1(@RequestParam(value = "msg") int masterId) {

        List<DetailEntity> detailEntityList = detailEntityRepository.findAllByMasterId(masterId);
        List<String> a = new ArrayList<>();
        for(DetailEntity detailEntity : detailEntityList){
            Optional<ItemListEntity> itemListEntityTemp = itemListEntityRepository.findById(detailEntity.getItemListId());
            a.add(itemListEntityTemp.get().getItemCode());
        }
        return new ResponseEntity<>(a , HttpStatus.OK);
    }

//    @GetMapping("test2")
//    public ResponseEntity<?> getTest2(@RequestParam(value = "msg") String testaaa) {
//
//        List<ItemListEntity> itemListEntityList = itemListEntityRepository.findnameaaa(testaaa);
//        System.out.println(itemListEntityList);
//        return new ResponseEntity<>(itemListEntityList , HttpStatus.OK);
//    }

}

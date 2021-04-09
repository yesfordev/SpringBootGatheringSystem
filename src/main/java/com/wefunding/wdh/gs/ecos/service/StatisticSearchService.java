package com.wefunding.wdh.gs.ecos.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefunding.wdh.gs.ecos.dto.search.Row;
import com.wefunding.wdh.gs.ecos.dto.search.SearchRes;
import com.wefunding.wdh.gs.ecos.entity.*;
import com.wefunding.wdh.gs.ecos.repository.*;
import com.wefunding.wdh.gs.ecos.utils.EcosUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class StatisticSearchService {

    @Autowired
    DetailEntityRepository detailEntityRepository;

    @Autowired
    MasterEntityRepository masterEntityRepository;

    @Autowired
    SearchEntityRepository searchEntityRepository;

    @Autowired
    ItemListEntityRepository itemListEntityRepository;

    @Autowired
    SearchHistoryEntityRepository searchHistoryEntityRepository;


    @Value("${serviceKey}")
    private String serviceKey;

    @Value("${ecosUrl}")
    private String ecosUrl;

    public String saveSearch(int masterId) throws IOException {

        String returnMsg = "";

        EcosUtils ecosUtils = new EcosUtils();

        List<DetailEntity> detailEntityList = detailEntityRepository.findAllByMasterId(masterId);

        //지표 상태 업데이트
        MasterEntity masterEntity = masterEntityRepository.findById(masterId);
        masterEntity.setStatus(2);
        masterEntityRepository.save(masterEntity);

        String flag = detailEntityList.get(0).getFlag();

        String GroupOne = "";
        String GroupTwo = "";
        String GroupThree = "";
        String urlstr = "";
        JSONObject resObject;


        Optional<ItemListEntity> itemListEntityTemp = null;

        if (flag.equals("1")) {

            for (DetailEntity detailEntityTemp : detailEntityList) {

                itemListEntityTemp = itemListEntityRepository.findById(detailEntityTemp.getItemListId());

                int startCount = 1;
                int finishCount = 2000;

                while (true) {

                    urlstr = ecosUrl +
                            serviceKey +
                            "/json/kr/" +
                            +startCount + "/"
                            + finishCount + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStatCode() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getCycle() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getStartTime() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getEndTime() + "/"
                            + itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();

                    resObject = ecosUtils.connectUrlReturnObject(urlstr);

                    ObjectMapper mapper = new ObjectMapper();
                    //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                    SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);
                    List<Row> rowList = searchRes.getStatisticSearch().getRow();

                    saveDataBase(resObject, rowList, masterId);

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

            for (DetailEntity detailEntityTemp : detailEntityList) {
                itemListEntityTemp = itemListEntityRepository.findById(detailEntityTemp.getItemListId());

                if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group1")) {
                    GroupOne = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                } else if (itemListEntityTemp.orElseThrow(NoSuchElementException::new).getGrpCode().equals("Group2")) {
                    GroupTwo = itemListEntityTemp.orElseThrow(NoSuchElementException::new).getItemCode();
                } else {
                    log.info("Database flag is wrong");
                    statusUpdate(1, masterId);
                    return "save failed";
                }
            }

            int startCount = 1;
            int finishCount = 2000;

            while (true) {
                urlstr = ecosUrl +
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

                resObject = ecosUtils.connectUrlReturnObject(urlstr);

                log.info("url: " + urlstr);
                log.info("RETURN ORIGIN" + resObject);

                ObjectMapper mapper = new ObjectMapper();
                //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);


                SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);
                List<Row> rowList = searchRes.getStatisticSearch().getRow();

                saveDataBase(resObject, rowList, masterId);


                if (finishCount < Integer.parseInt(itemListEntityTemp.orElseThrow(NoSuchElementException::new).getDataCnt())) {
                    startCount += 2000;
                    finishCount += 2000;
                } else {
                    break;
                }
            }
            log.info("master Id: " + masterId + "저장 완료");

        } else if (flag.equals("3")) {

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
                    statusUpdate(1, masterId);
                    return "save failed";
                }
            }

            int startCount = 1;
            int finishCount = 2000;

            while (true) {
                urlstr = ecosUrl +
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

                resObject = ecosUtils.connectUrlReturnObject(urlstr);

                log.info("url: " + urlstr);
                log.info("RETURN ORIGIN" + resObject);

                ObjectMapper mapper = new ObjectMapper();
                //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                SearchRes searchRes = mapper.readValue(resObject.toString(), SearchRes.class);
                List<Row> rowList = searchRes.getStatisticSearch().getRow();

                saveDataBase(resObject, rowList, masterId);

                if (finishCount < Integer.parseInt(itemListEntityTemp.orElseThrow().getDataCnt())) {
                    startCount += 2000;
                    finishCount += 2000;
                } else {
                    break;
                }
            }
            log.info("master Id: " + masterId + "저장 완료");


        } else {
            statusUpdate(1, masterId);
            return "masterId " + masterId + "의 flag 값이 잘못 되었습니다. flag:" + flag;

        }

        statusUpdate(0, masterId);

        return returnMsg;

    }

    @Transactional
    public String saveDataBase(JSONObject resObject, List<Row> rowList, int masterId){

        String returnMsg = "";

        try {

            List<SearchEntity> searchEntityList = new ArrayList<>();

            for (Row row : rowList) {
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


                SearchEntity searchEntity
                        = searchEntityRepository.findSearchEntityByMasterIdAndStatCodeAndItemCode1AndItemCode2AndItemCode3AndTime(
                        masterId,row.getSTAT_CODE(),row.getITEM_CODE1(),row.getITEM_CODE2(),row.getITEM_CODE3(),row.getTIME());

                /**
                 * 신규추가
                 */
                if(searchEntity == null){
                    searchEntityList.add(searchEntityTemp);

                }
                /**
                 * 변경업데이트 (이력저장 포함)
                 */
                else if(!searchEntity.getDataValue().equals(row.getDATA_VALUE())){

                    //이력저장
                    SearchHistoryEntity searchHistoryEntity = new SearchHistoryEntity();
                    searchHistoryEntity.setStatisticSearchId(searchEntity.getId());
                    searchHistoryEntity.setStatCode(searchEntity.getStatCode());
                    searchHistoryEntity.setStatName(searchEntity.getStatName());
                    searchHistoryEntity.setItemCode1(searchEntity.getItemCode1());
                    searchHistoryEntity.setItemName1(searchEntity.getItemName1());
                    searchHistoryEntity.setItemCode2(searchEntity.getItemCode2());
                    searchHistoryEntity.setItemName2(searchEntity.getItemName2());
                    searchHistoryEntity.setItemCode3(searchEntity.getItemCode3());
                    searchHistoryEntity.setItemName3(searchEntity.getItemName3());
                    searchHistoryEntity.setUnitName(searchEntity.getUnitName());
                    searchHistoryEntity.setTime(searchEntity.getTime());
                    searchHistoryEntity.setDataValue(searchEntity.getDataValue());
                    searchHistoryEntity.setMasterId(searchEntity.getMasterId());

                    searchHistoryEntityRepository.save(searchHistoryEntity);

                    //변경데이터 업데이트
                    searchEntity.setDataValue(row.getDATA_VALUE());
                    searchEntityRepository.save(searchEntity);
                }

            }

            searchEntityRepository.saveAll(searchEntityList);

        } catch (Exception e) {

            statusUpdate(1, masterId);

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

        return returnMsg;
    }

    public void statusUpdate(int status, int masterId){
        //지표 상태 업데이트
        MasterEntity masterEntity = masterEntityRepository.findById(masterId);
        masterEntity.setStatus(status);
        masterEntityRepository.save(masterEntity);
    }

    public Object getUpdateInfo(int masterId) {
        MasterEntity masterEntity = masterEntityRepository.findById(masterId);

        Map<String, Object> map = new HashMap<>();
        map.put("status", masterEntity.getStatus());
        map.put("rec_dt", masterEntity.getRecentUpdDt());

        return map;
    }
}

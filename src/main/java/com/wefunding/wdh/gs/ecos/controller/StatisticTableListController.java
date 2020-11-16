package com.wefunding.wdh.gs.ecos.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.wefunding.wdh.gs.ecos.dto.tablelist.Row;
import com.wefunding.wdh.gs.ecos.dto.tablelist.StatisticTableList;
import com.wefunding.wdh.gs.ecos.dto.tablelist.TableRes;
import com.wefunding.wdh.gs.ecos.entity.TableListEntity;
import com.wefunding.wdh.gs.ecos.repository.TableListEntityRepository;
import com.wefunding.wdh.gs.ecos.utils.EcosUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yes on 2020/11/13
 */

@RestController
@RequestMapping("/tablelist")
@RequiredArgsConstructor
public class StatisticTableListController {

    private static Logger LOGGER = LoggerFactory.getLogger(StatisticTableListController.class);

    private final TableListEntityRepository tableListEntityRepository;

    @Value("${serviceKey}")
    private String serviceKey;

    /**
     * 한국은행 api 통신 테스트
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/test")
    public String testTableList() throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        String urlstr = "http://ecos.bok.or.kr/api/StatisticTableList/" +
                serviceKey +
                "/json/kr/1/1000/";


        JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

//        LOGGER.info("RETURN ORIGIN" + resObject);

        ObjectMapper mapper = new ObjectMapper();

        //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        TableRes tableRes = mapper.readValue(resObject.toString(), TableRes.class);

        LOGGER.info("list_total_count: " + tableRes.getStatisticTableList().getList_total_count());
        LOGGER.info("총 아이탬 갯수: " + tableRes.getStatisticTableList().getRow().size());

        return "test complete";
    }

    /**
     * 서비스 통계 목록 전체 저장
     * @return
     * @throws IOException
     */

    @GetMapping("/saveAll")
    @Transactional
    public String saveTableList() throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        List<TableListEntity> tableListEntityList = new ArrayList<>();


        String urlstr = "http://ecos.bok.or.kr/api/StatisticTableList/" +
                serviceKey +
                "/json/kr/1/1000/";


        JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

//        LOGGER.info("RETURN ORIGIN" + resObject);

        ObjectMapper mapper = new ObjectMapper();

        //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        try {
            TableRes tableRes = mapper.readValue(resObject.toString(), TableRes.class);

            List<Row> rowList = tableRes.getStatisticTableList().getRow();

            for (Row row : rowList) {
                TableListEntity tableListEntityTemp = new TableListEntity();

                tableListEntityTemp.setPStatCode(row.getP_STAT_CODE());
                tableListEntityTemp.setCycle(row.getCYCLE());
                tableListEntityTemp.setOrgName(row.getORG_NAME());
                tableListEntityTemp.setSrchYn(row.getSRCH_YN());
                tableListEntityTemp.setStatCode(row.getSTAT_CODE());
                tableListEntityTemp.setStatName(row.getSTAT_NAME());

                tableListEntityList.add(tableListEntityTemp);
//                tableListEntityRepository.save(table)
            }

            tableListEntityRepository.saveAll(tableListEntityList);

//            int i = 1;
//
//            for(Row row : rowList) {
//                LOGGER.info(i+"번째 row");
//                LOGGER.info("P_STAT_CODE: "+ row.getP_STAT_CODE());
//                LOGGER.info("CYCLE: "+row.getCYCLE());
//                LOGGER.info("ORG_NAME: "+row.getORG_NAME());
//                LOGGER.info("SRCH_YN: "+row.getSRCH_YN());
//                LOGGER.info("STAT_CODE: "+row.getSTAT_CODE());
//                LOGGER.info("STAT_NAME: "+row.getSTAT_NAME());
//                i++;
//            }
        } catch (UnrecognizedPropertyException e) {
            String resString = resObject.toString();

            int start = resString.indexOf("CODE\":");
            int end = resString.indexOf("\"}}");

            String errCode = resString.substring(start + "CODE\":".length(), end);

            if (errCode.equals("500")) {
                return "서버 에러로 인하여 종료되었습니다.";
            }
        }
//        LOGGER.info("list_total_count: " + tableRes.getStatisticTableList().getList_total_count());
//        LOGGER.info("총 아이탬 갯수: " + tableRes.getStatisticTableList().getRow().size());


        return "save complete";
    }
}

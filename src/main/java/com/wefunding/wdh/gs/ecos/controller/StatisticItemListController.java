package com.wefunding.wdh.gs.ecos.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.wefunding.wdh.gs.ecos.dto.itemlist.ItemRes;
import com.wefunding.wdh.gs.ecos.dto.itemlist.Row;
import com.wefunding.wdh.gs.ecos.entity.ItemListEntity;
import com.wefunding.wdh.gs.ecos.entity.TableListEntity;
import com.wefunding.wdh.gs.ecos.repository.ItemListEntityRepository;
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
 * Created by yes on 2020/11/16
 */
@RestController
@RequestMapping("itemlist")
@RequiredArgsConstructor
public class StatisticItemListController {

    private static Logger LOGGER = LoggerFactory.getLogger(StatisticItemListController.class);

    private final ItemListEntityRepository itemListEntityRepository;

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
    public String testItemList() throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        List<TableListEntity> tableListEntityList = tableListEntityRepository.findAll();

        for(TableListEntity tableListEntity : tableListEntityList) {
            String urlstr = "http://ecos.bok.or.kr/api/StatisticItemList/" +
                    serviceKey +
                    "/json/kr/1/1000/"
                    + tableListEntity.getStatCode();

            JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

            ObjectMapper mapper = new ObjectMapper();

            //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            try {
                ItemRes itemRes = mapper.readValue(resObject.toString(), ItemRes.class);

                LOGGER.info("id: " + tableListEntity.getId());
                LOGGER.info("list_total_count: " + itemRes.getStatisticItemList().getList_total_count());
                LOGGER.info("총 아이템 갯수: " + itemRes.getStatisticItemList().getRow().size());
            } catch (UnrecognizedPropertyException e) {
                String resString = resObject.toString();

                int start = resString.indexOf("CODE\":");
                int end = resString.indexOf("\"}}");

                String errCode = resString.substring(start + "CODE\":".length(), end);

                if (errCode.equals("500")) {
                    return "서버 에러로 인하여 " + tableListEntity.getId() + "에서 종료되었습니다.";
                }
                LOGGER.info("id: " + tableListEntity.getId() + " 에서 데이터 없음");
            }
        }
        return "test complete";
    }

    /**
     * itemList 정보 전체 저장
     * @return
     * @throws IOException
     */

    @GetMapping("/saveAll")
    public String saveItemList() throws IOException {

        EcosUtils ecosUtils = new EcosUtils();

        List<TableListEntity> tableListEntityList = tableListEntityRepository.findAll();

        for(TableListEntity tableListEntity : tableListEntityList) {
            String urlstr = "http://ecos.bok.or.kr/api/StatisticItemList/" +
                    serviceKey +
                    "/json/kr/1/1000/"
                    + tableListEntity.getStatCode();

            JSONObject resObject = ecosUtils.connectUrlReturnObject(urlstr);

            ObjectMapper mapper = new ObjectMapper();

            //objectMapper가 json->vo로 변환시 대소문자 구분 가능하게 해주는 설정
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            try {
                ItemRes itemRes = mapper.readValue(resObject.toString(), ItemRes.class);

                List<Row> rowList = itemRes.getStatisticItemList().getRow();
                List<ItemListEntity> itemListEntityList = new ArrayList<>();

                for (Row row : rowList) {
                    ItemListEntity itemListEntityTemp = new ItemListEntity();

                    itemListEntityTemp.setStatCode(row.getSTAT_CODE());
                    itemListEntityTemp.setStatName(row.getSTAT_NAME());
                    itemListEntityTemp.setGrpCode(row.getGRP_CODE());
                    itemListEntityTemp.setGrpName(row.getGRP_NAME());
                    itemListEntityTemp.setPItemCode(row.getP_ITEM_CODE());
                    itemListEntityTemp.setItemCode(row.getITEM_CODE());
                    itemListEntityTemp.setItemName(row.getITEM_NAME());
                    itemListEntityTemp.setCycle(row.getCYCLE());
                    itemListEntityTemp.setStartTime(row.getSTART_TIME());
                    itemListEntityTemp.setEndTime(row.getEND_TIME());
                    itemListEntityTemp.setDataCnt(row.getDATA_CNT());

                    itemListEntityList.add(itemListEntityTemp);

                }

                itemListEntityRepository.saveAll(itemListEntityList);
                LOGGER.info("id: " + tableListEntity.getId() + " 저장 완료");

            } catch (UnrecognizedPropertyException e) {
                String resString = resObject.toString();

                int start = resString.indexOf("CODE\":");
                int end = resString.indexOf("\"}}");

                String errCode = resString.substring(start + "CODE\":".length(), end);

                if (errCode.equals("500")) {
                    return "서버 에러로 인하여 " + tableListEntity.getId() + " 에서 종료되었습니다.";
                }
                LOGGER.info("id: " + tableListEntity.getId() + " 에서 데이터 없음");
            }
        }
        return "test complete";
    }
}

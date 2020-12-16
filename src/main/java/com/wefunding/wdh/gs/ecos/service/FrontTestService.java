package com.wefunding.wdh.gs.ecos.service;

import com.wefunding.wdh.gs.ecos.dto.chart.ChartData;
import com.wefunding.wdh.gs.ecos.dto.chart.Datasets;
import com.wefunding.wdh.gs.ecos.entity.SearchEntity;
import com.wefunding.wdh.gs.ecos.repository.SearchEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by yes on 2020/12/16
 */
@Service
@RequiredArgsConstructor
public class FrontTestService {

    private final SearchEntityRepository searchEntityRepository;

    public Object getStatisticSearchChart(String cycle, int masterId, Optional<List<String>> itemName1, int startTime, int endTime) {

        List<String> labels = new ArrayList<>();
        List<Datasets> datasetList = new ArrayList<>();

        //년일 때
        if(cycle.equals("YY")) {
            for(int time = startTime; time <= endTime; time++) {
                labels.add(time + "년");
            }

            for(String itemName : itemName1.get()) {
                int time = startTime;
                List<Double> data = new ArrayList<>();

                while(time <= endTime) {
                    SearchEntity searchEntity = searchEntityRepository.findByAreaByTime(masterId, itemName, time);

                    if(searchEntity == null) {
                        data.add((double) 0);
                    } else {
                        data.add(Double.parseDouble(searchEntity.getDataValue()));
                    }
                    time++;
                }

                String hex = createRandomColor();

                Datasets datasets = new Datasets(data, itemName, hex, false);

                datasetList.add(datasets);
            }

            ChartData chartData = new ChartData(labels, datasetList);

            return chartData;
        } else if(cycle.equals("QQ")) {
            int labelTime = startTime;
            while(labelTime <= endTime) {
                labels.add(labelTime/10 + "년 " + labelTime%10 + "분기");
                labelTime++;

                if(labelTime%10 > 4) {
                    labelTime = labelTime - labelTime%10;
                    labelTime+=11;
                }
            }

            for(String itemName : itemName1.get()) {
                int time = startTime;
                List<Double> data = new ArrayList<>();

                while(time <= endTime) {
                    SearchEntity searchEntity = searchEntityRepository.findByAreaByTime(masterId, itemName, time);

                    if(searchEntity == null) {
                        data.add((double) 0);
                    } else {
                        data.add(Double.parseDouble(searchEntity.getDataValue()));
                    }

                    time++;
                    if(time%10 > 4) {
                        time = time - time%10;
                        time+=11;
                    }
                }

                String hex = createRandomColor();

                Datasets datasets = new Datasets(data, itemName, hex, false);

                datasetList.add(datasets);
            }

            ChartData chartData = new ChartData(labels, datasetList);

            return chartData;
        } else if(cycle.equals("MM")) {
            int labelTime = startTime;
            while(labelTime <= endTime) {
                labels.add(labelTime/100+ "년 " + labelTime%100 + "월");
                labelTime++;

                if(labelTime%100 > 12) {
                    labelTime = labelTime - labelTime%100;
                    labelTime+=101;
                }
            }

            for(String itemName : itemName1.get()) {
                int time = startTime;
                List<Double> data = new ArrayList<>();

                while(time <= endTime) {
                    SearchEntity searchEntity = searchEntityRepository.findByAreaByTime(masterId, itemName, time);
                    System.out.println(time);

                    if(searchEntity == null) {
                        data.add((double) 0);
                    } else {
                        System.out.println(searchEntity.getDataValue());
                        data.add(Double.parseDouble(searchEntity.getDataValue()));
                    }

                    time++;
                    if(time%100 > 12) {
                        time = time - time%100;
                        time+=101;
                    }
                }

                String hex = createRandomColor();

                Datasets datasets = new Datasets(data, itemName, hex, false);

                datasetList.add(datasets);
            }

            ChartData chartData = new ChartData(labels, datasetList);

            return chartData;
        }
        return null;
    }

    private String createRandomColor() {
        Random ra = new Random();
        int r, g, b;

        r=ra.nextInt(255);
        g=ra.nextInt(255);
        b=ra.nextInt(255);
        Color color = new Color(r,g,b);
        String hex = Integer.toHexString(color.getRGB() & 0xffffff);
        if (hex.length() < 6) {
            hex = "0" + hex;
        }
        hex = "#" + hex;

        return hex;
    }
}
package com.wefunding.wdh.gs.batch.config;

import com.wefunding.wdh.gs.batch.task.SampleTask1;
import com.wefunding.wdh.gs.batch.task.SampleTask2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SampleConfig {

    //https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configureJob
    /**
     * 테스트 시나리오
     * 1. (ItemReader)DB에서 처리해야할 job 수집대상펑션을 읽어온다
     * 2. (ItemProcessor)수집대상펑션을 순차적으로 처리한다
     * 3. 각 job(데이터수집목록)의 데이터 갱신/저장 상태(정상/비정상)를 알람(이메일)으로 담당자에게 전송한다.
     * 4. (ItemWriter)처리된 결과를 job 테이블에 저장한다.
     */

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Step stepOne(){
        return steps.get("stepOne")
                .tasklet(new SampleTask1())
                .build();
    }

    @Bean
    public Step stepTwo(){
        return steps.get("stepTwo")
                .tasklet(new SampleTask2())
                .build();
    }

    @Bean
    public Job demoJob(){
        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

}

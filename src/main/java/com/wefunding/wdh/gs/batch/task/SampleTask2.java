package com.wefunding.wdh.gs.batch.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class SampleTask2 implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleTask2.class);

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        LOGGER.info("두번째 작업 시작");

        //여기에 로직 추가

        LOGGER.info("두번째 작업 종료");


        return RepeatStatus.FINISHED;
    }

}

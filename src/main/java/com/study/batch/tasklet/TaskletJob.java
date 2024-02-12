package com.study.batch.tasklet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * BatchApplication
 * --job.name=taskletJob v=2 date=20240212
 * 1.job.name :: spring.batch.job.names: ${job.name:NONE} --> application.yml
 * 2.date :: #{jobParameters[date]}
 * */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TaskletJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletJobBatchBuild() {
        return jobBuilderFactory.get("taskletJob")
                .start(taskletJobStep01())
                .next(taskletJobStep02(null))
                .build();
    }

    @Bean
    public Step taskletJobStep01() {
        return stepBuilderFactory.get("taskletJobStep01")
                .tasklet((a, b) -> {
                    log.info("===== taskletJobStep01 =====");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step taskletJobStep02(@Value("#{jobParameters[date]}") String date) {
        return stepBuilderFactory.get("taskletJobStep01")
                .tasklet((a, b) -> {
                    log.info("===== taskletJobStep02 {} =====", date);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}

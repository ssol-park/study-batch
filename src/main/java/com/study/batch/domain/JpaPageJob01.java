package com.study.batch.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPageJob01 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job jpaPageJobBatchBuild01() {
        return jobBuilderFactory.get("jpaPageJobBatchBuild01")
                .start(jpaPageJobStep01())
                .build();
    }

    @Bean
    public Step jpaPageJobStep01() {
        return stepBuilderFactory.get("jpaPageJobStep01")
                .<Dept, Dept>chunk(chunkSize)
                .reader(jpaPageJobDbItemReader())
                .writer(jpaPageJobPrintItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Dept> jpaPageJobDbItemReader() {
        return new JpaPagingItemReaderBuilder<Dept>()
                .name("jpaPageJobDbItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM Dept d ORDER BY DEPT_NO")
                .build();
    }

    @Bean
    public ItemWriter<Dept> jpaPageJobPrintItemWriter() {
        return list -> list.forEach(dept -> log.info("deptNo:{} name:{} loc:{}", dept.getDeptNo(), dept.getName(), dept.getLoc()));
    }
}

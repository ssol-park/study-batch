package com.study.batch.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPageJob02 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job jpaPageJobBatchBuild02() {
        return jobBuilderFactory.get("jpaPageJob02")
                .start(jpaPageJobStep02())
                .build();
    }

    @Bean
    public Step jpaPageJobStep02() {
        return stepBuilderFactory.get("jpaPageJobStep02")
                .<Dept, Dept2>chunk(chunkSize)
                .reader(jpaPageJobDbItemReader02())
                .processor(jpaPageJobDbItemProcessor02())
                .writer(jpaPageJobJpaItemWriter02())
                .build();
    }

    private ItemProcessor<Dept, Dept2> jpaPageJobDbItemProcessor02() {
        return dept -> Dept2.builder()
                        .deptNo(dept.getDeptNo())
                        .name("NEW_" + dept.getName())
                        .loc("NEW_" + dept.getLoc())
                        .build();
    }

    @Bean
    public JpaPagingItemReader<Dept> jpaPageJobDbItemReader02() {
        return new JpaPagingItemReaderBuilder<Dept>()
                .name("jpaPageJobDbItemReader02")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM Dept d ORDER BY DEPT_NO")
                .build();
    }

    @Bean
    public JpaItemWriter<Dept2> jpaPageJobJpaItemWriter02() {
        JpaItemWriter jpaItemWriter = new JpaItemWriter();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;
    }
}

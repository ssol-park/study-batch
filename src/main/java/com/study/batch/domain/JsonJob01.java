package com.study.batch.domain;

import com.study.batch.dto.MarketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JsonJob01 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private static final int chunkSize = 5;

    @Bean
    public Job jsonJob01BatchBuild() {
        return jobBuilderFactory.get("jsonJob01")
                .start(jsonJob01Step())
                .build();
    }

    @Bean
    public JsonItemReader<MarketDto> jsonJob01Reader() {
        return new JsonItemReaderBuilder<MarketDto>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(MarketDto.class))
                .resource(new ClassPathResource("sample/sample01.json"))
                .name("jsonJob01Reader")
                .build();
    }

    @Bean
    public Step jsonJob01Step() {
        return stepBuilderFactory.get("jsonJob01Step")
                .<MarketDto, MarketDto>chunk(chunkSize)
                .reader(jsonJob01Reader())
                .writer(markets -> markets.forEach(market -> log.info("market:{}, koreanName:{}, englishName:{}", market.getMarket(), market.getKoreanName(), market.getEnglishName())))
                .build();
    }
}

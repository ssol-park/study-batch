package com.study.batch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MarketDto {
    private String market;
    private String koreanName;
    private String englishName;

    @Builder
    public MarketDto(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }
}

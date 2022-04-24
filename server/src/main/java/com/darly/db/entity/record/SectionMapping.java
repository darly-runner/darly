package com.darly.db.entity.record;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SectionMapping {
    private Float km;
    private Float pace;
    private Integer calories;

    @QueryProjection
    public SectionMapping(Float km, Float pace, Integer calories) {
        this.km = km;
        this.pace = pace;
        this.calories = calories;
    }
}

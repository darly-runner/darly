package com.darly.api.request.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCrewSearchModel {
    private Integer page;
    private Integer address;
    private String key;

    public GetCrewSearchModel(Integer page, Integer address) {
        this.page = page;
        this.address = address;
        this.key = "";
    }
}

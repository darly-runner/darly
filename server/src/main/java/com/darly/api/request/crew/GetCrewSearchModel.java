package com.darly.api.request.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCrewSearchModel {
    private Integer page;
    private Integer size;
    private Integer address;
    private String key;

    public GetCrewSearchModel(Integer page, Integer size, Integer address) {
        this.page = page;
        this.size = size;
        this.address = address;
        this.key = "";
    }
}

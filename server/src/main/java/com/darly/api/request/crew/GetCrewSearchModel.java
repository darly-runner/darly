package com.darly.api.request.crew;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

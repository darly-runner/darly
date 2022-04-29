package com.darly.api.response.crew;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.crew.CrewDetailMapping;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("CrewDetailGetResponse")
public class CrewDetailGetRes extends BaseResponseBody {
    private String crewName;
    private String crewDesc;
    private String crewNotice;
    private String crewHost;
    private String crewLocation;
    private String crewImage;
    private Long crewPeople;
    private Long crewFeedNum;

    @Builder
    public CrewDetailGetRes(Integer statusCode, String message, CrewDetailMapping crewDetailMapping) {
        super(statusCode, message);
        this.crewName = crewDetailMapping.getCrewName();
        this.crewDesc = crewDetailMapping.getCrewDesc();
        this.crewNotice = crewDetailMapping.getCrewNotice();
        this.crewHost = crewDetailMapping.getCrewHost();
        this.crewLocation = crewDetailMapping.getCrewLocation();
        this.crewImage = crewDetailMapping.getCrewImage();
        this.crewPeople = crewDetailMapping.getCrewPeople();
        this.crewFeedNum = crewDetailMapping.getCrewFeedNum();
    }
}

package com.darly.api.request.crew;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CrewCreatePostReq {
    private String crewName;
    private String crewDesc;
    private Long crewAddress;
    private String crewJoin;
    @Nullable
    private MultipartFile crewImage;
}

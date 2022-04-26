package com.darly.api.response.account;

import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@ApiModel("KakaoUserResponse")
public class KakaoUserRes {
    @ApiModelProperty(name="id", example="1")
    private Long id;
    @ApiModelProperty(name="kakao_account", example="kakao_account")
    private Map<String, Object> kakao_account;

    public User toEntity() {
        String email = (boolean)kakao_account.get("has_email") ? (String)kakao_account.get("email") : id.toString();
        return User.builder()
                .userNickname(email)
                .userEmail(email)
                .build();
    }
}

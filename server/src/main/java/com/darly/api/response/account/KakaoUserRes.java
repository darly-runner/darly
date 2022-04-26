package com.darly.api.response.account;

import com.darly.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("KakaoUserResponse")
public class KakaoUserRes {
    @ApiModelProperty(name="email", example="ssafy@ssafy.com")
    private String email;
    @ApiModelProperty(name="name", example="김싸피")
    private String name;

    public User toEntity(){
        return User.builder()
                .userNickname(name)
                .userEmail(email)
                .build();
    }
}

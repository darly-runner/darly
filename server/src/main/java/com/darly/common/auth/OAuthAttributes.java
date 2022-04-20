package com.darly.common.auth;

import com.darly.db.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.jar.Attributes;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeKey, Map<String, Object> attributes){
        if(registrationId.equals("kakao"))
            return ofKakao("email", attributes);
        return ofGoogle(userNameAttributeKey, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeKey)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeKey, Map<String,Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(kakaoAccount)
                .nameAttributeKey(userNameAttributeKey)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
//                .role(Role.GUEST) // 기본 권한 GUEST
                .build();
    }

}
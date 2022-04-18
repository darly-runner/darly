package com.darly.config;

import com.darly.api.service.User.UserService;
import com.darly.common.auth.CustomOAuth2UserService;
import com.darly.common.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity //Spring Security 설정 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private DarlyUserDetailService darlyUserDetailService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private UserService userService;
//
//    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // DAO 기반으로 Authentication Provider를 생성
//    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
//    @Bean
//    DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(this.darlyUserDetailService);
//        return daoAuthenticationProvider;
//    }
//
//    // DAO 기반의 Authentication Provider가 적용되도록 설정
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()// rest api 만을 고려하여 기본 설정은 해제 0
                .csrf().disable()// csrf 보안 토큰 disable처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음 0
                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                .authorizeRequests()//요청에 대한 사용권한 체크
                .antMatchers("/accounts").permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name()) //인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
                .anyRequest().authenticated()
                .and().cors()
                .and().oauth2Login().userInfoEndpoint(); // oauth2 로그인 성공 후 가져올 때의 설정들
        // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
//                .userService(customOAuth2UserService);    // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        super.configure(http);
    }
}


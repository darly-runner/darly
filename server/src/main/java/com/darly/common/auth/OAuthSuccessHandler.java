package com.darly.common.auth;

import com.darly.db.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//    private final TokenService tokenService;
//    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println(oAuth2User);
        getRedirectStrategy().sendRedirect(request, response, "/accounts/login?email="+oAuth2User.getAttributes().get("email"));
//        UserDto userDto = userRequestMapper.toDto(oAuth2User);
//
//        // 최초 로그인이라면 회원가입 처리를 한다.
//
//        Token token = tokenService.generateToken(userDto.getEmail(), "USER");
//        log.info("{}", token);
//
//        writeTokenResponse(response, token);
    }

//    private void writeTokenResponse(HttpServletResponse response, Token token)
//            throws IOException {
////        response.setContentType("text/html;charset=UTF-8");
////
////        response.addHeader("Auth", token.getToken());
////        response.addHeader("Refresh", token.getRefreshToken());
////        response.setContentType("application/json;charset=UTF-8");
////
////        var writer = response.getWriter();
////        writer.println(objectMapper.writeValueAsString(token));
////        writer.flush();
//    }
}

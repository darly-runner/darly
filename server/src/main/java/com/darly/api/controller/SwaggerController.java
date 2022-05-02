package com.darly.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class SwaggerController {

    // 기존의 주소를 좀더 간결하게 입력해서 들어가려고 만든 함수
    @GetMapping("/swagger")
    public void home(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String redirect_url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/swagger-ui/chat.html#";
        response.sendRedirect(redirect_url);
    }
}

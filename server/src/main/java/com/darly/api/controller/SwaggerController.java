package com.darly.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class SwaggerController {

    // 기존의 주소를 좀더 간결하게 입력해서 들어가려고 만든 함수
    @GetMapping("/swagger")
    public void home(HttpServletResponse response) throws IOException {
//        String redirect_url = "http://localhost:8080/swagger-ui/index.html#"; // 로컬에서 테스트할떄 리다이렉팅
        String redirect_url = "http://3.36.61.107:8000/swagger-ui/index.html#"; // ec2에서 리다이렉팅
        response.sendRedirect(redirect_url);
    }
}

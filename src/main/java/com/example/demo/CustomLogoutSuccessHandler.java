package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("kakao", authentication.getName());
        String token = client.getAccessToken().getTokenValue();

        Map<String, Object> paramMap = new HashMap<>();

//        paramMap.put("target_id_type", "user_id");
//        paramMap.put("target_id", "2899244426");

        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        String url = "https://kauth.kakao.com/oauth/logout";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", client.getClientRegistration().getClientId())
                .queryParam("logout_redirect_uri", "http://localhost:8088/question/list");

        System.out.println(builder.toUriString());
        System.out.println("sdklfjsldfjlskdfjlksdjflksjdflksjf");
//        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        String res = restTemplate.getForObject(builder.toUriString(), String.class);
        System.out.println(res);
//        ResponseEntity<String> res2 = restTemplate.postForEntity("https://kapi.kakao.com/v1/user/unlink",entity, String.class);
//        System.out.println(res.getBody());

//        System.out.println(res2);
        response.sendRedirect("http://127.0.0.1:8088/question/list");
    }

}

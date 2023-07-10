package com.example.demo.security;

import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2AccessToken token = userRequest.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token.getTokenValue());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<MyResponse> response = restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me", entity, MyResponse.class);
        String name = response.getBody().getProperties().get("nickname").toString();
        String id = response.getBody().getId();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_KAKAO"));

        MySocialUser mySocialUser = new MySocialUser(name, "1234", authorities);
        mySocialUser.setToken(token.getTokenValue());

        if (userRepository.findByLoginId(name).isEmpty()) {
            SiteUser user = new SiteUser();
            user.setLoginId(name);
            user.setPasswd(null);
            user.setEmail(null);
            userRepository.save(user);
        }
        return mySocialUser;
    }
}

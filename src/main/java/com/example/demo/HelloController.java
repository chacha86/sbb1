package com.example.demo;

import com.example.demo.question.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class HelloController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/extest")
    public String extest() {
        Optional<Question> oq = questionRepository.findById(123123);
        if(oq.isPresent()) {
            System.out.println("hihi");
        } else {
            throw new DataNotFoundException("kkk");
        }

        return "tail_test";
    }
    @GetMapping("/hello")
    public String hello() {
        return "tail_test";
    }
    @GetMapping("/show")
    public String show() {
        return "/fragment/nav.html";
    }

    @GetMapping("/auth")
    public String auth(Model model) {
        String targetUrl = "https://kauth.kakao.com/oauth/authorize";
        String clientId = "a0072d1885fe5cbccfeb539059439e9f";
        String redirectUri = "http://localhost:8088/login/auth2/code/kakao";
        String responseType = "code";

        try {
            redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> param = new HashMap<>();
        param.put("targetUrl", targetUrl);
        param.put("clientId", clientId);
        param.put("redirectUri", redirectUri);
        param.put("repsonseType", responseType);

        model.addAttribute("client_id", clientId);
        model.addAttribute("redirect_uri", redirectUri);
        model.addAttribute("response_type", responseType);

        return "auth_test";

    }


////    /sakljdflaksjfd;lkdfasdfaklsdfjklasdjfaasdfkjlksajdfklj
//    //sdfsdfsdf


    @GetMapping("regform")
    public String regform() {
        return "/note_test";
    }
    @GetMapping("regtest")
    public String regtest(@RequestParam("editordata") String data) {
        System.out.println("123123");
        System.out.println(data);
        return "redirect:/question/list";
    }
}

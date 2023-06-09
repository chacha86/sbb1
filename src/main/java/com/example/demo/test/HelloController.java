package com.example.demo.test;

import com.example.demo.common.EmailService;
import com.example.demo.question.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import com.sun.jna.platform.win32.Sspi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Controller
public class HelloController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    EmailService emailService;

    @GetMapping("/")
    public String index(Principal principal) {
        System.out.println(principal.getName());
        return "redirect:/question/list";
    }

    @GetMapping("/extest")
    public String extest() {
        Optional<Question> oq = questionRepository.findById(123123);
        if (oq.isPresent()) {
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

    @GetMapping("/toast")
    public String toast() {
        return "test/toast_test";
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


    @GetMapping("imgupForm")
    public String imgupForm() {
        return "test/upload_test_form";
    }

//    @PostMapping("imgup")
//    public String imgup(@RequestParam("file") MultipartFile file) {
//
//        String targetDir = "C:/work/img/";
//        File tmp = new File(targetDir);
//
//        if (!tmp.exists() && !tmp.isFile()) {
//            tmp.mkdirs();
//        }
//        String filename = getUniqueFile(file.getOriginalFilename());
//        String filePath = targetDir + filename;
//
//        try {
//            file.transferTo(new File(filePath));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "redirect:/question/list";
//    }

    @GetMapping("/mailtest")
    @ResponseBody
    public String mailtest() {
        emailService.sendMail("taejin0619@naver.com", "이건 테스트용 메일이야!", "아주 잘 가는 구먼!!");
        return "메일 발송 성공!";
    }

}

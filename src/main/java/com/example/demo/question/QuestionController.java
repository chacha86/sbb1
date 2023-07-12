package com.example.demo.question;

import com.example.demo.answer.AnswerForm;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam (defaultValue = "0") Integer pageNo, Authentication authentication) {


        Integer itemPerPage = 10;
        Pageable pageable = PageRequest.of(pageNo, itemPerPage);
        Page<Question> paging = questionService.getList(pageable);
        long total = questionService.getTotalCount();

        model.addAttribute("paging", paging);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("total", total/10);

        return "question/question_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/form")
    public String questionForm(QuestionForm questionForm) {
        return "question/question_form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "question/question_form";
        }

        SiteUser user = userService.getUserbyLoginId(principal.getName());
        questionService.insert(questionForm.getSubject(), questionForm.getContent(), user);

        return "redirect:/question/list";
    }
    @GetMapping("detail/{questionId}")
    public String detail(AnswerForm answerForm, @PathVariable("questionId") int id, Model model) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/question_detail";
    }
    @GetMapping("/show")
    public String show() {
        return "question/question_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recommend/{id}")
    public String recommend(Principal principal, @PathVariable("id") int id) {
        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUserbyLoginId(principal.getName());
        questionService.recommend(user, question);

        return String.format("redirect:/question/detail/%d",id);
    }

    @PostMapping("/imgup")
    @ResponseBody
    public String imgup(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {


        String targetDir = "/static/";
        File tmp = new File(targetDir);
        if (!tmp.exists() && !tmp.isFile()) {
            tmp.mkdirs();
        }

        Resource resource = new ClassPathResource("static");
        Path staticPath = Paths.get(resource.getURI());
        String uniqueFileName = getUniqueFile(file.getOriginalFilename());
        Path filePath = staticPath.resolve(uniqueFileName);

        file.transferTo(filePath);

        // getScheme()는 요청 스킴(예: http 또는 https)을 반환합니다.
        String scheme = request.getScheme();

        // getServerName()는 호스트 이름을 반환합니다.
        String serverName = request.getServerName();

        // getServerPort()는 서버의 포트 번호를 반환합니다.
        int serverPort = request.getServerPort();

        // 호스트 URL을 조합합니다.
        String host;
        if (serverPort == 80 || serverPort == 443) {
            host = scheme + "://" + serverName;
        } else {
            host = scheme + "://" + serverName + ":" + serverPort;
        }

        String url = host + "/" + uniqueFileName;
        Map<String, Object> jsonParam = new HashMap<>();

        System.out.println(url);
        jsonParam.put("url", url);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jsonParam);
        return json;
    }

    private String getUniqueFile(String filename) {
        long millis = System.currentTimeMillis();
        String result = millis + "_" + filename;

        return result;
    }
}

package com.example.demo.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/add")
    @ResponseBody
    public String add(@RequestParam("subject") String subject,
                      @RequestParam("content") String content) {
        questionService.insert(subject, content);

        return "질문 등록 성공!!";
    }
    @GetMapping("/show")
    public String show() {
        System.out.println("showshowshow21!!!");
        return "question/question_list";
    }
}

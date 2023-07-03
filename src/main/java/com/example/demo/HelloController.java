package com.example.demo;

import com.example.demo.question.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

//    /sakljdflaksjfd;lkj
}

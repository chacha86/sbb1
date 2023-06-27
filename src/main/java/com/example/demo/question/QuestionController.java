package com.example.demo.question;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam (defaultValue = "0") Integer pageNo) {
        Integer itemPerPage = 10;
        Pageable pageable = PageRequest.of(pageNo, itemPerPage);
        Page<Question> questionList = questionService.getList(pageable);
        model.addAttribute("questionList", questionList);
        return "question/question_list";
    }

    @GetMapping("/form")
    public String questionForm(QuestionForm questionForm) {
        return "question/question_form";
    }

    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "question/question_form";
        }

        questionService.insert(questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/list";
    }
    @GetMapping("/show")
    public String show() {
        return "question/question_list";
    }
}

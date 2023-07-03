package com.example.demo.question;

import com.example.demo.answer.AnswerForm;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam (defaultValue = "1") Integer pageNo) {
        Integer itemPerPage = 10;
        Pageable pageable = PageRequest.of(pageNo, itemPerPage);
        Page<Question> paging = questionService.getList(pageable);
        long total = questionService.getTotalCount();
        model.addAttribute("paging", paging);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("total", total/10);

        return "question/question_list";
    }

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
}
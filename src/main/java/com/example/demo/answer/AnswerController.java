package com.example.demo.answer;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionController;
import com.example.demo.question.QuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;
    @PostMapping("/create/{questionId}")
    public String create(@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal,
                         @PathVariable("questionId") int questionId, Model model) {

        Question q1 = questionService.getQuestion(questionId);
        model.addAttribute("question", q1);

        SiteUser user = userService.getUserbyLoginId(principal.getName());
        if(!bindingResult.hasErrors()) {
            answerService.create(answerForm.getContent(), q1, user);
        }

        return String.format("redirect:/question/detail/%d", questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recommend/{id}")
    public String recommend(Principal principal, @PathVariable("id") int id) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUserbyLoginId(principal.getName());
        answerService.recommend(user, answer);
        return String.format("redirect:/question/detail/%d", answer.getQuestion().getId());
    }

}

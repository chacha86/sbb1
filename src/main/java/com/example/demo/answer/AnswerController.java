package com.example.demo.answer;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionController;
import com.example.demo.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    @PostMapping("/create/{questionId}")
    public String create(@Valid AnswerForm answerForm, BindingResult bindingResult,
                         @PathVariable("questionId") int questionId, Model model) {

        Question q1 = questionService.getQuestion(questionId);
        model.addAttribute("question", q1);

        if(!bindingResult.hasErrors()) {
            answerService.create(answerForm.getContent(), q1);
        }

        return "question/question_detail";
    }


}

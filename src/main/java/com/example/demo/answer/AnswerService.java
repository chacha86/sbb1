package com.example.demo.answer;


import com.example.demo.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(String content, Question question) {
        Answer a = new Answer();

        a.setContent(content);
        a.setQuestion(question);
        a.setCreateDate(LocalDateTime.now());

        answerRepository.save(a);
    }
}

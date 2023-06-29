package com.example.demo.answer;


import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(String content, Question question, SiteUser user) {
        Answer a = new Answer();

        a.setContent(content);
        a.setQuestion(question);
        a.setCreateDate(LocalDateTime.now());
        a.setAuthor(user);

        answerRepository.save(a);
    }
}

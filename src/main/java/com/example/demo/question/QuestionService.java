package com.example.demo.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public void insert(String subject, String content) {
        Question q1 = new Question();
        q1.setSubject(subject);
        q1.setContent(content);
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);
    }

    public List<Question> getList() {
        List<Question> questionList = questionRepository.findAll();
        return questionList;
    }

}

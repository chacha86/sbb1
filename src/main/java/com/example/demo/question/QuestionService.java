package com.example.demo.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Question getQuestion(int id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        if(optionalQuestion.isPresent()) {
            return optionalQuestion.get();
        }
        else {
            throw new DataNotFoundException("not found question");
        }

    }
    public void insert(String subject, String content) {
        Question q1 = new Question();
        q1.setSubject(subject);
        q1.setContent(content);
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);
    }

    public Page<Question> getList(Pageable pageable) {
        Page<Question> questionList = questionRepository.findAll(pageable);
        return questionList;
    }

    public long getTotalCount() {
        return questionRepository.count();
    }
}

package com.example.demo.answer;


import com.example.demo.Recommendation.*;
import com.example.demo.question.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerRecommendationRepository answerRecommendationRepository;
    public void create(String content, Question question, SiteUser user) {
        Answer a = new Answer();

        a.setContent(content);
        a.setQuestion(question);
        a.setAuthor(user);

        answerRepository.save(a);
    }

    public Answer getAnswer(int id) {
        Optional<Answer> ao = answerRepository.findById(id);
        if(ao.isPresent()) {
            return ao.get();
        } else {
            throw new DataNotFoundException("not found answer");
        }
    }

    public void recommend(SiteUser user, Answer answer) {
        Optional<AnswerRecommendation> ao = answerRecommendationRepository.findByUserAndAnswer(user, answer);

        if(ao.isPresent()) {
            answerRecommendationRepository.delete(ao.get());
            return;
        }

        AnswerRecommendation recommendation = new AnswerRecommendation();
        recommendation.setUser(user);
        recommendation.setId(new AnswerRecommendationKey(answer.getId(), user.getId()));
        answer.addAnswerRecommendation(recommendation);
        answerRecommendationRepository.save(recommendation);
    }
}

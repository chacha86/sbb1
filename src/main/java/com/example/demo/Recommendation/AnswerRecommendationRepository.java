package com.example.demo.Recommendation;

import com.example.demo.answer.Answer;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRecommendationRepository extends JpaRepository<AnswerRecommendation, AnswerRecommendationKey> {
    Long countByAnswer(Answer answer);

    Optional<AnswerRecommendation> findByUserAndAnswer(SiteUser user, Answer answer);
}

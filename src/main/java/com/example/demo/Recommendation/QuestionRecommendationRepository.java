package com.example.demo.Recommendation;

import com.example.demo.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRecommendationRepository extends JpaRepository<QuestionRecommendation, QuestionRecommendationKey> {
    Long countByQuestion(Question question);
}

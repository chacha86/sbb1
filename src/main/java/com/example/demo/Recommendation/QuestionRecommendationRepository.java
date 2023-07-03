package com.example.demo.Recommendation;

import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRecommendationRepository extends JpaRepository<QuestionRecommendation, QuestionRecommendationKey> {
    Long countByQuestion(Question question);

    Optional<QuestionRecommendation> findByUserAndQuestion(SiteUser user, Question question);
}

package com.example.demo.Recommendation;

import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.time.LocalDateTime;

@Entity
public class QuestionRecommendation {
    @EmbeddedId
    private QuestionRecommendationKey id;

    @MapsId("questionId")
    @ManyToOne
    private Question question;
    @MapsId("userId")
    @ManyToOne
    private SiteUser user;

    private LocalDateTime createDate;
}


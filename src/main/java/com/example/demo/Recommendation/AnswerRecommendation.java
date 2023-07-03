package com.example.demo.Recommendation;

import com.example.demo.answer.Answer;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class AnswerRecommendation {
    @EmbeddedId
    private AnswerRecommendationKey id;

    @MapsId("answerId")
    @ManyToOne
    private Answer answer ;

    @MapsId("userId")
    @ManyToOne
    private SiteUser user;
}


package com.example.demo.Recommendation;

import com.example.demo.answer.Answer;
import com.example.demo.user.SiteUser;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class AnswerRecommendation {

    private Answer answer;
    private SiteUser user;

}

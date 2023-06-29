package com.example.demo;

import com.example.demo.answer.Answer;
import com.example.demo.user.SiteUser;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class AnswerRecommendation {

    @
    private Answer answer;
    private SiteUser user;

}

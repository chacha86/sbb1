package com.example.demo.question;

import com.example.demo.Recommendation.QuestionRecommendation;
import com.example.demo.answer.Answer;
import com.example.demo.common.BaseEntity;
import com.example.demo.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
public class Question extends BaseEntity {

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private SiteUser author;

    @OneToMany(mappedBy = "question", cascade=CascadeType.REMOVE)
    private List<QuestionRecommendation> qrlist;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}

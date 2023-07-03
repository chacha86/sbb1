package com.example.demo.answer;

import com.example.demo.Recommendation.AnswerRecommendation;
import com.example.demo.common.BaseEntity;
import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class Answer extends BaseEntity {

    private String content;
    @ManyToOne
    private SiteUser author;
    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "answer", cascade=CascadeType.REMOVE)
    private List<AnswerRecommendation> arlist;

    public void addAnswerRecommendation(AnswerRecommendation recommendation) {
        recommendation.setAnswer(this);
        this.arlist.add(recommendation);
    }
}

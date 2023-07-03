package com.example.demo.Recommendation;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRecommendationKey implements Serializable {
    private Long questionId;
    private Long userId;
}

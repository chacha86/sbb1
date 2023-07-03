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
public class AnswerRecommendationKey implements Serializable {
    private Long answerId;
    private Long userId;
}

package com.example.demo.user;

import com.example.demo.answer.Answer;
import com.example.demo.common.BaseEntity;
import com.example.demo.question.Question;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SiteUser extends BaseEntity {
    @Column(length = 30, unique = true)
    private String loginId;
    private String passwd;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();
}

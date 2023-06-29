package com.example.demo.answer;

import com.example.demo.question.Question;
import com.example.demo.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.SimpleFormatter;

@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @ManyToOne
    private SiteUser author;
    @ManyToOne
    private Question question;
    @ManyToMany
    private Set<SiteUser> voter;

    private LocalDateTime createDate;

}

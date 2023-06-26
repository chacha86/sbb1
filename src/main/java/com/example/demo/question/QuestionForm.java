package com.example.demo.question;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수입니다.")
    private String subject;
    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
}

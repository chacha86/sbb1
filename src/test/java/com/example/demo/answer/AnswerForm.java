package com.example.demo.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {
    @NotEmpty(message = "내용을 필수입니다.")
    private String content;
}

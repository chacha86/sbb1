package com.example.demo.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FindForm {
    @NotEmpty(message = "email을 입력해주세요.")
    private String email;
}

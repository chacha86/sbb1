package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {
    @NotEmpty(message = "아이디는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String passwd1;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String passwd2;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    private String email;
}

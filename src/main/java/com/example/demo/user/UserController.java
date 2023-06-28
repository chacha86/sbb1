package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/create")
    public String create(UserForm userForm) {
        return "signup_form";
    }
    @PostMapping("/create")
    public String create(@Valid UserForm userForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "signup_form";
        }
        userService.create(userForm.getLoginId(), userForm.getPasswd1(), userForm.getEmail());

        return "redirect:/question/list";
    }

    @GetMapping("login")
    public String login() {
        return "login_form";
    }
}

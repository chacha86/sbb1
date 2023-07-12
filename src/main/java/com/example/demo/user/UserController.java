package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/create")
    public String create(UserForm userForm) {
        return "auth/signup_form";
    }
    @PostMapping("/create")
    public String create(@Valid UserForm userForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "auth/signup_form";
        }
        userService.create(userForm.getLoginId(), userForm.getPasswd1(), userForm.getEmail());

        return "redirect:/question/list";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login_form";
    }

    @GetMapping("/find/{target}")
    public String findForm(@PathVariable("target") String target, FindForm findForm) {
        if(target.equals("id"))
            return "auth/find_id_form";
        else if(target.equals("pw"))
            return "auth/find_pw_form";
        else
            return "redirect:/user/login";
    }
    @PostMapping("/find/{target}")
    public String doFind(@PathVariable("target") String target, @Valid FindForm findForm,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "auth/find_" + target + "_form";
        }

        String destination = "auth/find_" + target + "_form";
        return "redirect:/question/list";
    }
}

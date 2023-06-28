package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String loginId, String passwd, String email) {
        User user = new User();
        user.setLoginId(loginId);
        user.setPasswd(passwordEncoder.encode(passwd));
        user.setEmail(email);
        user.setCrateDate(LocalDateTime.now());

        userRepository.save(user);
    }
}

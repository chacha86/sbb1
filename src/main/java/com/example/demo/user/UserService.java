package com.example.demo.user;

import com.example.demo.question.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser getUserbyLoginId(String loginId) {
        Optional<SiteUser> siteUser = userRepository.findByLoginId(loginId);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("not found user");
        }
    }
    public void create(String loginId, String passwd, String email) {
        SiteUser user = new SiteUser();
        user.setLoginId(loginId);
        user.setPasswd(passwordEncoder.encode(passwd));
        user.setEmail(email);

        userRepository.save(user);
    }
}

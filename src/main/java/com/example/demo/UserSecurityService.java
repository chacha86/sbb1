package com.example.demo;

import com.example.demo.question.DataNotFoundException;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> userOptional = userRepository.findByLoginId(username);
        if(userOptional.isEmpty()) {
            throw new DataNotFoundException("not found user");
        }
        SiteUser siteUser = userOptional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("Admin"));
        } else {
            authorities.add(new SimpleGrantedAuthority("User"));
        }

        return new User(siteUser.getLoginId(), siteUser.getPasswd(), authorities);

    }
}

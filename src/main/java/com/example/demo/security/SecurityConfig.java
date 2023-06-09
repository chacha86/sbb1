package com.example.demo.security;

import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;
//    @Autowired
//    CustomerOAuth2UserService customOAuth2UserService;
    @Autowired
    private UserRepository userRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                new AntPathRequestMatcher("/**")
        )
                .permitAll()
                .and()
                .csrf(
                        csrf -> csrf.disable()
                )
                .formLogin()
                .loginPage("/user/login").defaultSuccessUrl("/question/list")
                .and()
                .logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/user/logout"))
                .invalidateHttpSession(true)
//                .logoutSuccessHandler(successHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );
        return http.build();
    }
//    @Bean
//    public WebClient rest(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
//        return WebClient.builder()
//                .filter(oauth2).build();
//    }

    @Bean
    @Transactional
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        System.out.println("sldfkjlskdjflksj");
        return request -> {

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add((GrantedAuthority) () -> "KAKAO_USER");
            OAuth2User user = delegate.loadUser(request);
            System.out.println(user.getName());
            System.out.println(request.getClientRegistration().getRegistrationId());
            if (!"kakao".equals(request.getClientRegistration().getRegistrationId())) {
                return user;
            }
            else {
                userRepository.save(new SiteUser(user.getName(), null, authorities));
                return user;
            }
        };
    }

    @Bean
    LogoutSuccessHandler successHandler() {
        return new CustomLogoutSuccessHandler();
    }


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
//    }
//    private ClientRegistration googleClientRegistration() {
//        return ClientRegistration.withRegistrationId("kakao")
//                .clientId("a0072d1885fe5cbccfeb539059439e9f")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:8088/login/auth2/code/kakao")
//                .scope("profile_nickname", "account_email")
//                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
//                .tokenUri("https://kauth.kakao.com/oauth/token")
//                .userInfoUri("https://kapi.kakao.com/v2/user/me")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .clientName("kakao")
//                .build();
//    }
}

package com.example.demo.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30, unique = true)
    private String loginId;
    private String passwd;
    @Column(unique = true)
    private String email;
    private LocalDateTime crateDate;
}

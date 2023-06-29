package com.example.demo.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SiteUser {
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

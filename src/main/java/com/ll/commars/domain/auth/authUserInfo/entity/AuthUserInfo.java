package com.ll.commars.domain.auth.authUserInfo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor // 모든 필드에 대한 생성자가 자동으로 만들어진다.
public class AuthUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private List<String> roles = new ArrayList<>();

    public AuthUserInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public void addRole(String roleName){
        roles.add(roleName);
    }
    private LocalDateTime loginTime;
}
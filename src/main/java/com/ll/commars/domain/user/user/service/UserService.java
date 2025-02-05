package com.ll.commars.domain.user.user.service;

// 트랜잭션 단위로 실행될 메소드를 선언하고 있는 클래스
// 스프링이 관리하는 Bean

import com.ll.commars.domain.user.user.entity.User;
import com.ll.commars.domain.user.user.repository.UserRepository;
import com.ll.commars.domain.user.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public User createUser(String email, String name, Integer socialProvider, String password, String phoneNumber, String profileImageUrl, LocalDateTime birthDate,Integer gender) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSocialProvider(socialProvider);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setProfileImageUrl(profileImageUrl);
        user.setBirthDate(birthDate);
        user.setGender(gender);
        // ... 기타 필드 설정 ...
        return userRepository.save(user);
    }

    public User addUser(String email, String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password); // 평문 비밀번호 저장
        return userRepository.save(user);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("이미 등록된 이메일입니다.");
        }

        // 비밀번호 해싱 (Spring Security 사용 고려)
        user.setPassword(user.getPassword());

        userRepository.save(user);
    }


    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User authenticate(String email, String password) {
        logger.info("Authenticating user with email: {}", email);
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null);
    }

    public void truncate() {
        userRepository.deleteAll();
    }



}

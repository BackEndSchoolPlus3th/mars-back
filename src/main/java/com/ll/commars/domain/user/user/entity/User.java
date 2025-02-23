package com.ll.commars.domain.user.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.commars.domain.community.board.entity.Board;
import com.ll.commars.domain.community.comment.entity.Comment;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.domain.user.favorite.entity.Favorite;
import com.ll.commars.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1: 카카오, 2: 네이버, 3: 구글
    @NotNull
    @Column(name = "social_provider", nullable = false)
    private Integer socialProvider;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

//    @Column(name = "password")
//    private String password;

//    @Column(name = "login_id", nullable = true) // null 허용
//    private String loginId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    // 1: 남성, 2: 여성
    @Column(name = "gender")
    private Integer gender;

    // User와 Review: 일대다
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    // User와 Favorite: 일대다
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Favorite> favorites;

    // User와 Board: 일대다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Board> boards;

    // User와 Comment: 일대다
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;
}

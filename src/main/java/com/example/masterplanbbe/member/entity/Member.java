package com.example.masterplanbbe.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    private Long id;
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private String profileImageUrl;
    private String role;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;

    public static Member create(Long id, String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl, String role) {
      Member member = new Member();
      member.id = id;
      member.userId = userId;
      member.email = email;
      member.name = name;
      member.nickname = nickname;
      member.password = password;
      member.phoneNumber = phoneNumber;
      member.birthday = birthday;
      member.profileImageUrl = profileImageUrl;
      member.role = role;
      member.createTime = LocalDateTime.now();
      member.modifiedTime = LocalDateTime.now();
      return member;
    }

    public void update(String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl, String role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        modifiedTime = LocalDateTime.now();
    }


}

package com.example.masterplanbbe.member.entity;

import com.example.masterplanbbe.common.domain.IdAndCreatedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends IdAndCreatedEntity {

    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private String profileImageUrl;
    private String role;

    public static Member create(String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl, String role) {
        Member member = new Member();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.userId = userId;
        member.email = email;
        member.name = name;
        member.nickname = nickname;
        member.password = password;
        member.phoneNumber = phoneNumber;
        member.birthday = birthday;
        member.profileImageUrl = profileImageUrl;
        member.role = role;

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
        setModifiedAt(LocalDateTime.now());
    }


}

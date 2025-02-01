package com.example.masterplanbbe.member.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends FullAuditEntity {

    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private String profileImageUrl;

    public static Member create(String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl) {
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

        return member;
    }

    @Builder
    public Member(String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.profileImageUrl = profileImageUrl;
    }

    public void update(String userId, String email, String name, String nickname, String password, String phoneNumber, LocalDate birthday, String profileImageUrl) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.profileImageUrl = profileImageUrl;
    }


}

package com.example.masterplanbbe.member.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.member.dto.MemberCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends FullAuditEntity {

    @Column(unique = true, nullable = false)
    private String userId;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    public static Member create(MemberCreateRequest request, String password, MemberRoleEnum role) {
        Member member = new Member();

        member.userId = request.getUserId();
        member.email = request.getEmail();
        member.name = request.getName();
        member.nickname = request.getUserId();
        member.password = password;

        // 수정 필요
        member.phoneNumber = "010-0000-0000";
        member.birthday = LocalDate.of(1995, 3, 23);
        member.profileImageUrl = "IMG_URL";
        member.role = role;

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

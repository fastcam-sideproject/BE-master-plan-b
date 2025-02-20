package com.example.masterplanbbe.member.entity;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import com.example.masterplanbbe.member.dto.MemberCreateRequest;
import com.example.masterplanbbe.security.dto.OAuth2UserDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Boolean isOAuth2;

    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    public static Member create(MemberCreateRequest request, String password, MemberRoleEnum role) {
        Member member = new Member();

        member.userId = request.getUserId();
        member.email = request.getEmail();
        member.name = request.getName();
        member.nickname = request.getUserId();
        member.password = password;
        member.isOAuth2 = false;

        // 수정 필요
        member.phoneNumber = "010-0000-0000";
        member.birthday = LocalDate.of(1995, 3, 23);
        member.profileImageUrl = "IMG_URL";
        member.role = role;

        return member;
    }

    public static Member create(OAuth2UserDTO dto) {
        Member member = new Member();

        member.userId = dto.userId();
        member.email = dto.email();
        member.name = dto.nickname(); // OAuth 2.0으로는 사용자명까지 받아오기가 힘든 것 같은데..?
        member.nickname = dto.nickname();
        member.profileImageUrl = dto.profileImage();
        member.role = dto.role();
        member.isOAuth2 = true;
        member.password = null;

        // 수정 필요
        member.phoneNumber = "010-0000-0000";
        member.birthday = LocalDate.of(1995, 3, 23);

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

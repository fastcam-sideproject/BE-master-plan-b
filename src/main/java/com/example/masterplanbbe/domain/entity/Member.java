package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import com.example.masterplanbbe.presentation.request.MemberCreateRequestDTO;
import com.example.masterplanbbe.infrastructure.security.dto.OAuth2UserDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends FullAuditEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String password;

    @Column
    private LocalDate birthdate;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false)
    private Boolean isOAuth2;

    @Column(nullable = false)
    private Boolean isAgreed;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleEnum role;

    // Custom Member Create
    public Member(MemberCreateRequestDTO request, String password, MemberRoleEnum role) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.password = password;
        this.birthdate = null;
        this.profileImageUrl = "IMG_URL";
        this.isOAuth2 = false;
        this.isAgreed = request.getIsAgreed();
        this.role = role;
    }

    // OAuth 2.0 Member Create
    public Member(OAuth2UserDTO dto) {
        this.email = dto.email();
        this.nickname = dto.nickname();
        this.password = null;
        this.birthdate = null;
        this.profileImageUrl = "IMG_URL";
        this.isOAuth2 = true;
        this.isAgreed = false; // 이거 마이페이지에서 수정하게 해야 되려나?
        this.role = MemberRoleEnum.USER;
    }
}

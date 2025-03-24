package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.domain.common.FullAuditEntity;
import com.example.masterplanbbe.presentation.request.MemberSpecRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_spec")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberSpec extends FullAuditEntity {

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private String specName;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private Long specNumber;

    @Column(nullable = false)
    private LocalDateTime achievementDate;

    @Column(nullable = false)
    private LocalDateTime expiredDate;

    public void updateMemberSpec(MemberSpecRequest memberSpecRequest) {
        this.achievementDate = memberSpecRequest.achievementDate();
        this.expiredDate = memberSpecRequest.expiredDate();
        this.specNumber = memberSpecRequest.specNumber();
        this.specName = memberSpecRequest.specName();
    }


}

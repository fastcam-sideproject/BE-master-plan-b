package com.example.masterplanbbe.domain.fixture;

import com.example.masterplanbbe.domain.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.List;

public class SecurityFixture {
    public static Principal createMockPrincipal(Member member) {
        return new UsernamePasswordAuthenticationToken(
                member.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority(member.getRole().getRole()))
        );
    }

}

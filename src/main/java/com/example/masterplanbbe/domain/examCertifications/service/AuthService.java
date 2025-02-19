package com.example.masterplanbbe.domain.examCertifications.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    
    // TODO 임시 userId 꺼내오는 메서드
    //  추후 spring bean 혹은 annotation으로 통합 예정
    public String getUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}

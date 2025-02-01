package com.example.masterplanbbe.member.api;

import com.example.masterplanbbe.member.service.request.MemberCreateRequest;
import com.example.masterplanbbe.member.service.request.MemberUpdateRequest;
import com.example.masterplanbbe.member.service.response.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class MemberApiTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Getter
    @AllArgsConstructor
    static class MemberCreateRequest {
        private String userId;
        private String email;
        private String name;
        private String nickname;
        private String password;
        private String phoneNumber;
        private LocalDate birthday;
        private String profileImageUrl;
        private String role;
    }

    @Getter
    @AllArgsConstructor
    static class MemberUpdateRequest {
        private String userId;
        private String email;
        private String name;
        private String nickname;
        private String password;
        private String phoneNumber;
        private LocalDate birthday;
        private String profileImageUrl;
        private String role;
    }

    @Test
    void createTest() {
        MemberResponse response = create(new MemberCreateRequest(
                "test1","test1@gmail.com","testmname", "testNick1",
                "testpassword","01012345678", LocalDate.of(2000,1,1), "","1"
        ));
        System.out.println("response = " + response);
    }

    MemberResponse create(MemberCreateRequest request) {
        return restClient.post()
                .uri("/api/v1/member")
                .body(request)
                .retrieve()
                .body(MemberResponse.class);
    }

    @Test
    void readTest() {
        MemberResponse response = read(1L);
        System.out.println("response = " + response);
    }

    MemberResponse read(Long id) {
        return restClient.get()
                .uri("/api/v1/member/{id}", id)
                .retrieve()
                .body(MemberResponse.class);
    }

    @Test
    void updateTest() {
        update(1L);
        MemberResponse response = read(1L);
        System.out.println("response = " + response);
    }

    void update(Long id) {
        restClient.put()
                .uri("/api/v1/member/{id}", id)
                .body(new MemberUpdateRequest(
                        "uptest1","uptest1@gmail.com","uptestmname", "uptestNick1",
                        "uptestpassword","up01012345678", LocalDate.of(2000,1,1), "","1"))
                .retrieve();
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/api/v1/member/{id}", 1L)
                .retrieve();
    }


}


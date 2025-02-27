package com.example.masterplanbbe.member.api;

import com.example.masterplanbbe.domain.member.dto.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class MemberApiTest {
    RestClient restClient = RestClient.create("http://localhost:8080");

}


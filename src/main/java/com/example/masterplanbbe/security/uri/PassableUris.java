package com.example.masterplanbbe.security.uri;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PassableUris {
    public static final Set<String> PASSABLE_URI = new HashSet<>() {{
        add("/api/v1/member/create");
        add("/api/v1/member/login");
        add("/api/v1/member/test");
    }};
}

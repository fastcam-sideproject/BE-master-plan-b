package com.example.masterplanbbe.domain.post.entity;

public enum Category {
    TIP("시험 꿀팁"),
    SHARE("자료 공유"),
    PLACE("시험장"),
    PROBLEM("시험 문제"),
    CHECK("합격자 조회");

    private final String name;

    Category(String name) {
        this.name = name;
    }


}

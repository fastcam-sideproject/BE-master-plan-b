package com.example.masterplanbbe.member.controller;

import com.example.masterplanbbe.member.service.MemberService;
import com.example.masterplanbbe.member.service.request.MemberCreateRequest;
import com.example.masterplanbbe.member.service.request.MemberUpdateRequest;
import com.example.masterplanbbe.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/v1/member/{id}")
    public MemberResponse find(@PathVariable("id") Long articleId) {
        return memberService.find(articleId);
    }

    @PostMapping("/api/v1/member")
    public MemberResponse create(@RequestBody MemberCreateRequest request) {
        return memberService.create(request);
    }

    @PutMapping("/api/v1/member/{id}")
    public MemberResponse update(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        return memberService.update(id, request);
    }

    @DeleteMapping("/api/v1/member/{id}")
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }


}

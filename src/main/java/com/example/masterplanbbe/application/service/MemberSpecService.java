package com.example.masterplanbbe.application.service;

import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.entity.MemberSpec;
import com.example.masterplanbbe.domain.entity.Spec;
import com.example.masterplanbbe.domain.repository.MemberRepositoryPort;
import com.example.masterplanbbe.domain.repository.MemberSpecRepositoryPort;
import com.example.masterplanbbe.domain.repository.SpecRepositoryPort;
import com.example.masterplanbbe.presentation.request.MemberSpecRequest;
import com.example.masterplanbbe.presentation.response.MemberSpecResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberSpecService {

    private final MemberSpecRepositoryPort memberSpecRepositoryPort;
    private final SpecRepositoryPort specRepositoryPort;
    private final MemberRepositoryPort memberRepositoryPort;

    @Transactional
    public MemberSpecResponse createMemberSpec(String email, MemberSpecRequest memberSpecRequest) {
        Member member = memberRepositoryPort.findByEmail(email);
        Spec spec = specRepositoryPort.findByName(memberSpecRequest.specName());
        MemberSpec memberSpec = MemberSpec.create(member, spec, memberSpecRequest);

        memberSpecRepositoryPort.save(memberSpec);

        return MemberSpecResponse.from(memberSpec);
    }

    public List<MemberSpecResponse> getMemberSpecList(String email) {
        Member member = memberRepositoryPort.findByEmail(email);
        List<MemberSpec> memberSpecs = memberSpecRepositoryPort.findAllByMember(member);

        return memberSpecs.stream().map(MemberSpecResponse::from).toList();
    }

    @Transactional
    public MemberSpecResponse updateMemberSpec(String email, Long memberSpecId, MemberSpecRequest memberSpecRequest) {
        Member member = memberRepositoryPort.findByEmail(email);
        MemberSpec memberSpec = memberSpecRepositoryPort.findById(memberSpecId);

        memberSpec.updateMemberSpec(memberSpecRequest);
        memberSpecRepositoryPort.save(memberSpec);

        return MemberSpecResponse.from(memberSpec);
    }

    public void deleteMemberSpec(String email, Long memberSpecId) {
        Member member = memberRepositoryPort.findByEmail(email);
        MemberSpec memberSpec = memberSpecRepositoryPort.findById(memberSpecId);

        memberSpecRepositoryPort.deleteById(memberSpecId);
    }
}

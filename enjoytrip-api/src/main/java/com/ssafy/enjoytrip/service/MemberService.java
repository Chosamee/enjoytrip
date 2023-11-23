package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.Member;
import com.ssafy.enjoytrip.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long regist(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .build();
        checkDuplicateMember(member); // 중복 회원 검증
        memberRepository.saveAndFlush(member);
        return member.getId();
    }

    public Long login(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .build();
        if (memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword()).isEmpty())
            throw new IllegalStateException("이메일 비밀번호 확인");
        return member.getId();
    }

    private void checkDuplicateMember(Member member) {
        if (!memberRepository.findByEmail(member.getEmail()).isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public MemberDto findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).get();
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        return memberDto;
    }

    public void saveRefreshToken(String email, String refreshToken) {
        memberRepository.saveRefreshToken(email, refreshToken);
    }

    public Object getRefreshToken(String email) {
        return memberRepository.findByEmail(email).get().getToken();
    }

    public void deleteRefreshToken(String email) {
        memberRepository.deleteRefreshToken(email);
    }
}

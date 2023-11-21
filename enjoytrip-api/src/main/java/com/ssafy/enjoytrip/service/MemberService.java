package com.ssafy.enjoytrip.service;

import java.util.List;
import javax.persistence.NoResultException;

import com.ssafy.enjoytrip.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long regist(Member member) {
        checkDuplicateMember(member); // 중복 회원 검증
        memberRepository.saveAndFlush(member);
        return member.getId();
    }

    public Long login(Member member) {
        try {
            memberRepository.findByEmailAndPassword(member.getEmail(), member.getPassword());
        } catch (Exception e) {
            throw new IllegalStateException("이메일 비밀번호 확인");
        }
        return member.getId();
    }

    private void checkDuplicateMember(Member member) {
        try {
            memberRepository.findByEmail(member.getEmail());
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } catch (NoResultException e) {

        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).get();
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

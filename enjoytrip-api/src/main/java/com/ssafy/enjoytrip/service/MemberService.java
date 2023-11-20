package com.ssafy.enjoytrip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.ssafy.enjoytrip.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.Member;
import com.ssafy.enjoytrip.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final SampleRepository sampleRepository;

    // 회원 가입
    @Transactional
    public Long regist(Member member) {
        checkDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    public Long login(Member member) {
        try {
            memberRepository.findByEmailPassword(member.getEmail(), member.getPassword());
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
        return memberRepository.findOne(memberId);
    }

    public void saveRefreshToken(String email, String refreshToken) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("email", email);
//        map.put("token", refreshToken);
//        memberRepository.saveRefreshToken(map);
        Member member = Member.builder()
                .email(email)
                .token(refreshToken)
                .build();
        sampleRepository.saveAndFlush(member);
    }

    public Object getRefreshToken(String userId) {
        return memberRepository.getRefreshToken(userId);
    }

    public void deleRefreshToken(String email) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", email);
        map.put("token", null);
        memberRepository.deleteRefreshToken(map);
    }
}

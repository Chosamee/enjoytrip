package com.ssafy.enjoytrip.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ssafy.enjoytrip.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Member findByEmailPassword(String email, String password) {
        return em.createQuery("select m from Member m where m.email = :email and m.password = :password", Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }

    public void saveRefreshToken(Map<String, String> map) {
        findByEmail(map.get("email")).setToken(null);
    }

    public Object getRefreshToken(String email) {
        return findByEmail(email).getToken();
    }

    public void deleteRefreshToken(Map<String, String> map) {
        em.remove(findByEmail(map.get("email")).getToken());
    }
}

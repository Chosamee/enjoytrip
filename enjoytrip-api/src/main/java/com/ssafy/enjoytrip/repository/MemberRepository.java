package com.ssafy.enjoytrip.repository;

import com.ssafy.enjoytrip.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.token=:token where m.email=:email")
    void saveRefreshToken(String email, String token);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.token=NULL where m.email=:email")
    void deleteRefreshToken(String email);
}

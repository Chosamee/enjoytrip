package com.ssafy.enjoytrip.repository;

import com.ssafy.enjoytrip.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Member, Long>
{
    Optional<Member> findByEmail(String email);


}

package com.ssafy.enjoytrip.repository;

import com.ssafy.enjoytrip.domain.Board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByEmail(String email);

    Optional<Board> findByArticleno(Long articleno);

    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.hit=b.hit+1 where b.articleno=:articleno")
    void updateHit(Long articleno);

    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.title=:title, b.content=:content where b.articleno=:articleno")
    void modifyArticle(Long articleno, String title, String content);
}

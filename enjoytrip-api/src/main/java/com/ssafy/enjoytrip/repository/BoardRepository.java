package com.ssafy.enjoytrip.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ssafy.enjoytrip.domain.Board;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }
}

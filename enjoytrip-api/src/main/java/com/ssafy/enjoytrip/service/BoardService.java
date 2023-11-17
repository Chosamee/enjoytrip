package com.ssafy.enjoytrip.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.Board;
import com.ssafy.enjoytrip.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void writeArticle(Board board) throws Exception {
        boardRepository.save(board);
    }
}

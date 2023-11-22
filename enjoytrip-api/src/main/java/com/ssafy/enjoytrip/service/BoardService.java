package com.ssafy.enjoytrip.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.domain.Board;
import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BoardListDto;
import com.ssafy.enjoytrip.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void writeArticle(Board board) {
        boardRepository.saveAndFlush(board);
    }

    public BoardListDto listArticle() {
        BoardListDto boardListDto = new BoardListDto();
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = boards.stream()
                .map(o -> {
                    BoardDto boardDto = new BoardDto();
                    boardDto.setContent(o.getContent());
                    boardDto.setEmail(o.getEmail());
                    boardDto.setHit(o.getHit());
                    boardDto.setTitle(o.getTitle());
                    boardDto.setArticleno(o.getArticleno());
                    return boardDto;
                }).collect(Collectors.toList());
        boardListDto.setArticles(boardDtos);
        return boardListDto;
    }

    public void updateHit(Long articleno) {
        boardRepository.updateHit(articleno);
    }

    public BoardDto getArticle(Long articleno) {
        BoardDto boardDto = new BoardDto();
        Board board = boardRepository.findByArticleno(articleno).get();
        boardDto.setArticleno(articleno);
        boardDto.setContent(board.getContent());
        boardDto.setTitle(board.getTitle());
        boardDto.setHit(board.getHit());
        boardDto.setEmail(board.getEmail());
        return boardDto;
    }

    public void modifyArticle(BoardDto boardDto) {
        Board board = Board.builder()
                .articleno(boardDto.getArticleno())
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .build();
        boardRepository.modifyArticle(board.getArticleno(), board.getTitle(), board.getContent());
    }
}

package com.ssafy.enjoytrip.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public BoardListDto listArticle(Map<String, String> map) {
        BoardListDto boardListDto = new BoardListDto();
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
        // int start = currentPage * sizePerPage - sizePerPage;

        Pageable paging = PageRequest.of(currentPage - 1, sizePerPage, Sort.Direction.DESC, "articleno");
        List<Board> boards = boardRepository.findByArticlenoGreaterThan(0L, paging);
        int totalArticleCount = boards.size();
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

        List<BoardDto> boardDtos = boards.stream()
                .map(o -> {
                    BoardDto boardDto = new BoardDto();
                    boardDto.setContent(o.getContent());
                    boardDto.setEmail(o.getEmail());
                    boardDto.setHit(o.getHit());
                    boardDto.setTitle(o.getTitle());
                    boardDto.setArticleno(o.getArticleno());
                    boardDto.setCreatedDate(o.getCreatedDate());
                    return boardDto;
                }).collect(Collectors.toList());
        boardListDto.setArticles(boardDtos);
        boardListDto.setCurrentPage(currentPage);
        boardListDto.setTotalPageCount(totalPageCount);
        return boardListDto;
    }

    @Transactional
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
        boardDto.setCreatedDate(board.getCreatedDate());
        return boardDto;
    }

    @Transactional
    public void modifyArticle(BoardDto boardDto) {
        Board board = Board.builder()
                .articleno(boardDto.getArticleno())
                .content(boardDto.getContent())
                .title(boardDto.getTitle())
                .build();
        boardRepository.modifyArticle(board.getArticleno(), board.getTitle(), board.getContent());
    }
}

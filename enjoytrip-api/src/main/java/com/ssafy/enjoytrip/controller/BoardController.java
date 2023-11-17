package com.ssafy.enjoytrip.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.domain.Board;
import com.ssafy.enjoytrip.domain.Member;
import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.service.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Autowired
    private HttpSession session;

    @ApiOperation(value = "글 작성")
    @PostMapping
    public ResponseEntity<?> writeArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) BoardDto boardDto) {
        log.info("writeArticle boardDto - {}", boardDto);
        try {
            Member member;
            if ((member = (Member) session.getAttribute("LOGIN_MEMBER")) == null)
                throw new IllegalAccessError();
            Board board = new Board();
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            board.setUserEmail(member.getEmail());
            boardService.writeArticle(board);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.ssafy.enjoytrip.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.domain.Board;
import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.BoardListDto;
import com.ssafy.enjoytrip.service.BoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @ApiOperation(value = "글 작성")
    @PostMapping
    public ResponseEntity<?> writeArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) BoardDto boardDto) {
        log.info("writeArticle boardDto - {}", boardDto.toString());
        try {
            Board board = new Board();
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            board.setEmail(boardDto.getEmail());
            System.out.println(board.toString());
            boardService.writeArticle(board);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
    @GetMapping
    public ResponseEntity<?> listArticle(
            @RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
        log.info("listArticle map - {}", map);
        try {
            BoardListDto boardListDto = boardService.listArticle();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(boardListDto);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
    @GetMapping("/{articleno}")
    public ResponseEntity<BoardDto> getArticle(
            @PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) Long articleno)
            throws Exception {
        log.info("getArticle - 호출 : " + articleno);
        boardService.updateHit(articleno);
        return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
    }

    @ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
    @GetMapping("/modify/{articleno}")
    public ResponseEntity<BoardDto> getModifyArticle(
            @PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) Long articleno)
            throws Exception {
        log.info("getModifyArticle - 호출 : " + articleno);
        return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping
    public ResponseEntity<String> modifyArticle(
            @RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
        log.info("modifyArticle - 호출 {}", boardDto);

        boardService.modifyArticle(boardDto);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

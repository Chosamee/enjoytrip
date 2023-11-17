package com.ssafy.enjoytrip.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.domain.Member;
import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.service.MemberService;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    // private static final String SUCCESS = "success";
    // private static final String FAIL = "fail";

    private final MemberService memberService;

    @Autowired
    private HttpSession session;

    @Operation(summary = "regist member", description = "회원가입")
    @PostMapping("/regist")
    public ResponseEntity<?> registMemeber(
            @RequestBody @ApiParam(value = "멤버 정보.", required = true) MemberDto memberDto) {
        logger.info("registMember memberDto - {}", memberDto.toString());
        try {
            Member member = new Member();
            member.setEmail(memberDto.getEmail());
            member.setName(memberDto.getName());
            member.setPassword(memberDto.getPassword());
            memberService.regist(member);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping()
    public ResponseEntity<?> loginMember(
            @RequestBody @ApiParam(value = "멤버 정보.", required = true) MemberDto memberDto) {
        logger.info("loginMember memberDto - {}", memberDto.toString());
        try {
            Member member = new Member();
            member.setEmail(memberDto.getEmail());
            member.setPassword(memberDto.getPassword());
            memberService.login(member);
            session.setAttribute("LOGIN_MEMBER", member);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

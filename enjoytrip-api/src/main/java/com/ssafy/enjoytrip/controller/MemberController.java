package com.ssafy.enjoytrip.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.domain.Member;
import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.service.MemberService;
import com.ssafy.enjoytrip.util.JWTUtil;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "regist member", description = "회원가입")
    @PostMapping("/regist")
    public ResponseEntity<?> registMemeber(
            @RequestBody @ApiParam(value = "멤버 정보.", required = true) MemberDto memberDto) {
        log.info("registMember memberDto - {}", memberDto.toString());
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

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(
            @RequestBody @ApiParam(value = "멤버 정보.", required = true) MemberDto memberDto) {
        log.info("loginMember memberDto - {}", memberDto.toString());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            Member member = new Member();
            member.setEmail(memberDto.getEmail());
            member.setPassword(memberDto.getPassword());
            memberService.login(member);

            String accessToken = jwtUtil.createAccessToken(memberDto.getEmail());
            String refreshToken = jwtUtil.createRefreshToken(memberDto.getEmail());
            System.out.println(accessToken);
            memberService.saveRefreshToken(memberDto.getEmail(), refreshToken);

            resultMap.put("access-token", accessToken);
            resultMap.put("refresh-token", refreshToken);

            status = HttpStatus.CREATED;

        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.ssafy.enjoytrip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.service.MemberService;
import com.ssafy.enjoytrip.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
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
            memberService.regist(memberDto);
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
            memberService.login(memberDto);

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

    @Operation(summary = "로그아웃", description = "회원 정보를 담은 Token을 제거한다.")
    @GetMapping("/logout/{email}")
    public ResponseEntity<?> removeToken(
            @PathVariable("email") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String email) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            memberService.deleteRefreshToken(email);
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, memberDto : {}", token, memberDto);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(memberDto.getEmail()))) {
                String accessToken = jwtUtil.createAccessToken(memberDto.getEmail());
                log.debug("token : {}", accessToken);
                log.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                status = HttpStatus.CREATED;
            }
        } else {
            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{email}")
    public ResponseEntity<Map<String, Object>> getInfo(
            @PathVariable("email") @ApiParam(value = "인증할 회원의 아이디.", required = true) String email,
            HttpServletRequest request) {
        // logger.debug("userId : {} ", userId);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
            log.info("사용 가능한 토큰!!!");
            try {
                // 로그인 사용자 정보.
                MemberDto memberDto = memberService.findByEmail(email);
                resultMap.put("userInfo", memberDto);
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("사용 불가능 토큰!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

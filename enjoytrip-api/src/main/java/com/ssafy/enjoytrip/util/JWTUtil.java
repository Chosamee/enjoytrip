package com.ssafy.enjoytrip.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.ssafy.enjoytrip.exception.UnAuthorizedException;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class JWTUtil {

	@Value("${jwt.salt}")
	private String salt;

	@Value("${jwt.access-token.expiretime}")
	private long accessTokenExpireTiem;

	@Value("${jwt.refresh-token.expiretime}")
	private long refreshTokenExpireTime;

	@Value("${jwt.secret}")
	private String secretKey;

	public String createAccessToken(String userId) {
		return create(userId, "access-token", accessTokenExpireTiem);
	}

	// AccessToken에 비해 유효기간을 길게 설정.
	public String createRefreshToken(String userId) {
		return create(userId, "refresh-token", refreshTokenExpireTime);
	}

	// Token 발급
	// key : Claim에 셋팅될 key 값
	// value : Claim에 셋팅 될 data 값
	// subject : payload에 sub의 value로 들어갈 subject값
	// expire : 토큰 유효기간 설정을 위한 값
	// jwt 토큰의 구성 : header + payload + signature
	private String create(String userId, String subject, long expireTime) {
		Date now = new Date();
		return JWT.create()    // JWT 토큰을 생성하는 빌더 반환
				.withSubject(subject) // JWT의 subject 지정 -> accessToken
				.withExpiresAt(new Date(now.getTime() + expireTime)) // 토큰 만료 시간 설정
				//클레임으로 uid, email 사용.
				//추가적으로 식별자나, 이름 등의 정보를 더 추가하셔도 됩니다.
				//추가하실 경우 .withClaim(클래임 이름, 클래임 값) 으로 설정해주시면 됩니다
				.withClaim("email", userId)
				.sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화

	}

	// 전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	public boolean checkToken(String token) {
		try {
			// Json Web Signature? 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
			// setSigningKey : JWS 서명 검증을 위한 secret key 세팅
			// parseClaimsJws : 파싱하여 원본 jws 만들기
			JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);// Claims 는 Map의 구현체 형태
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public String getUserId(String authorization) {
		try {
			return JWT.require(Algorithm.HMAC512(secretKey))
					.build()
					.verify(authorization)
					.getClaim("email")
					.toString();
		} catch (Exception e) {
			log.error("extractGithubId :: 액세스 토큰이 유효하지 않습니다.");
			e.printStackTrace();
			throw new UnAuthorizedException();
		}
	}

}

package com.fonada.masking.token.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fonada.masking.token.domain.TokenEntity;
import com.fonada.masking.token.model.PBClaims;
import com.fonada.masking.token.repository.TokenRepository;
import com.sun.org.slf4j.internal.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	@Resource
	private TokenRepository tokenRepository;

	private static final String SECRET_KEY = "lWbF6jNkG8PE0AUP";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	public String generateTokenForPasswordReset(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createTokenSmallExpiry(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	private String createTokenSmallExpiry(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String generateTokenForPB(final String secretKey) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", PBClaims.NAME);
		claims.put("siteId", PBClaims.SITE_ID);
		return createTokenForPB(claims, PBClaims.SUB, secretKey);
	}

	private String createTokenForPB(Map<String, Object> claims, String subject, final String secretKey) {
		Header header = Jwts.header();
		header.setType("JWT");
		String jwt = null;
		try {
			jwt = Jwts.builder().setHeader((Map<String, Object>) header).setClaims(claims).setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")).compact();
		} catch (Exception e) {
			System.out.println("CreateTokenForPB Got Exception:"+e.getMessage());
			//LOGGER.error("Got Exception createTokenForPB - ", e);
		}
		return jwt;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		boolean validToken = false;
		TokenEntity tokenEntity = tokenRepository.getTokenStoreByUserNameAndIsActive(username, "Y");
		if (tokenEntity != null && tokenEntity.getJwt().equals(token)) {
			validToken = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}

		return validToken;
	}
}
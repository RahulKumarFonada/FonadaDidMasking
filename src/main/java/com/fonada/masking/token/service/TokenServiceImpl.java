package com.fonada.masking.token.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.token.domain.TokenEntity;
import com.fonada.masking.token.model.CustomUserDetails;
import com.fonada.masking.token.repository.TokenRepository;
import com.fonada.masking.token.utils.JwtUtil;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * Created by sbsingh on Oct/27/2020.
 */
@Service
public class TokenServiceImpl implements TokenService {

	@Resource
	private TokenRepository tokenRepository;
	@Resource
	private UserRepository userRepository;
	@Autowired
	private CustomUserDetailService userDetailService;
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Override
	public void removeToken(String userName) {
		TokenEntity token = tokenRepository.getTokenStoreByUserNameAndIsActive(userName, "Y");
		if (token != null) {
			tokenRepository.delete(token);
			System.out.println("Token Expired Successfully");
		}
	}

	@Override
	public void saveToken(String userName, String jwt) {
		TokenEntity tokenEntity = new TokenEntity();
		tokenEntity.setUserName(userName);
		tokenEntity.setJwt(jwt);
		tokenEntity.setCreateDtm(new Date());
		tokenEntity.setIsActive("Y");
		tokenRepository.save(tokenEntity);
		System.out.println("Token Stored Successfully");
	}

	@Override
	public String getToken(String userName) {
		String jwt = null;
		TokenEntity token = tokenRepository.getTokenStoreByUserNameAndIsActive(userName, "Y");
		if (token != null) {
			jwt = token.getJwt();
		}
		return jwt;
	}

	@Override
	public String getToken(Long userId) {
		Users userEntity = userRepository.findByIdAndIsActive(Integer.valueOf(String.valueOf(userId)), "1");
		CustomUserDetails userDetails = (CustomUserDetails) userDetailService
				.loadUserByUsername(userEntity.getUsername());
		String jwt = jwtTokenUtil.generateTokenForPasswordReset(userDetails);
		saveToken(userDetails.getUsername(), jwt);
		return jwt;
	}
}

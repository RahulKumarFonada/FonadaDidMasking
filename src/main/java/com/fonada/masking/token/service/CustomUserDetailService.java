package com.fonada.masking.token.service;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.Users;
import com.fonada.masking.repository.UserRepository;
import com.fonada.masking.token.model.CustomUserDetails;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Resource
	private UserRepository userRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		CustomUserDetails customUserDetails = null;
		Users userEntity = null;
		userEntity = userRepository.findByUsername(userName);
		if (Objects.isNull(userEntity)) {
			throw new UsernameNotFoundException(Constants.USER_DOSE_NOT_AVAILABLE);
		} else {
			// userEntity.orElseThrow(() -> new UsernameNotFoundException("Not found: " +
			// userName));
			customUserDetails = new CustomUserDetails(userEntity);
		}
		return customUserDetails;
	}
}

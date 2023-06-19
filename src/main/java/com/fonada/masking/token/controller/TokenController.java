package com.fonada.masking.token.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fonada.masking.token.model.AuthenticationRequest;
import com.fonada.masking.token.model.AuthenticationResponse;
import com.fonada.masking.token.model.CustomUserDetails;
import com.fonada.masking.token.service.CustomUserDetailService;
import com.fonada.masking.token.service.TokenServiceImpl;
import com.fonada.masking.token.utils.JwtUtil;

@CrossOrigin
@Controller
@RequestMapping(value = "/auth")
@Component("com.fonada.masking")
public class TokenController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private CustomUserDetailService userDetailService;
	@Autowired
	private TokenServiceImpl tokenService;

	@RequestMapping(value = "/token", method = POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) userDetailService.loadUserByUsername(authenticationRequest.getUsername());

			if (Objects.isNull(userDetails)) {
				throw new Exception("Incorrect username or password");

			} else if (!userDetails.isAccountNonExpired()) {
				throw new UsernameNotFoundException("User Account Expired due to Inactivity");
			}
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect username or password", e);
		}

		String jwt = null;

		tokenService.removeToken(userDetails.getUsername());
		jwt = jwtTokenUtil.generateToken(userDetails);
		tokenService.saveToken(userDetails.getUsername(), jwt);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@GetMapping(name = "/checkUser")
	public ResponseEntity<?> checkUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		return ResponseEntity.ok(new AuthenticationResponse(username));

	}

	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout(@RequestBody AuthenticationRequest authenticationRequest) {
		tokenService.removeToken(authenticationRequest.getUsername());
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("Success");
	}
}

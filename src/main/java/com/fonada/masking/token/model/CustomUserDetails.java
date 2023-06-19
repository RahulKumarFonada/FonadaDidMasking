package com.fonada.masking.token.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fonada.masking.entity.Users;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

	private String username;
	private String password;
	private boolean active;
	private Integer userId;
	private long rolesId;
	private List<GrantedAuthority> authorities;
	private String passwordSalt;

	public CustomUserDetails(Users userEntity) {
		this.username = userEntity.getUsername();
		this.password = userEntity.getPassword();
		this.active = userEntity.getIsActive().equalsIgnoreCase("1") ? true : false;
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(userEntity.getRolesByRolesId().getName()));
		this.userId = userEntity.getId();
		this.rolesId = userEntity.getRoleId();
		this.passwordSalt = userEntity.getPasswordsalt();
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public long getRolesId() {
		return rolesId;
	}

	public void setRolesId(long rolesId) {
		this.rolesId = rolesId;
	}

}

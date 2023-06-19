package com.fonada.masking.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.token.domain.TokenEntity;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

	TokenEntity getTokenStoreByUserNameAndIsActive(String userName, String isActive);
}

package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.DidBlockedNumberEntity;

@Repository
@Transactional
public interface DidBloackedNumberRepository extends JpaRepository<DidBlockedNumberEntity, Long> {

	DidBlockedNumberEntity findOneById(Long id);

}

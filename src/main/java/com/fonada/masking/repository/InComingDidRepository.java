package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.InComingDidEntity;

@Repository
@Transactional
public interface InComingDidRepository extends JpaRepository<InComingDidEntity, Long> {

	@Transactional 
	@Query(value = "select * from incoming_did_no where exten like %:exten%", nativeQuery = true)
	InComingDidEntity findbyExtenContaining(@Param("exten") String exten);
	

}
 
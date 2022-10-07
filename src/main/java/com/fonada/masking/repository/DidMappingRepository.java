package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.DidMappingEntity;

@Repository
@Transactional
public interface DidMappingRepository extends JpaRepository<DidMappingEntity, Long> {

	@Query(value = "select * from did_mapping where did_number=:didNumber", nativeQuery = true)
	DidMappingEntity findbyDidNumber(@Param("didNumber") String didNumber);
	
	void deleteByDidNumber(String did);
	
	void deleteAllByDidNumber(String did);
}

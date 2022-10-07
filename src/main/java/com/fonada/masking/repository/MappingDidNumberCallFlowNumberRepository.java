package com.fonada.masking.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.MappingDidCallFlowNumber;

@Repository
@Transactional
public interface MappingDidNumberCallFlowNumberRepository extends JpaRepository<MappingDidCallFlowNumber, Long> {

	List<MappingDidCallFlowNumber> findAllByDidNumber(String didNumber);

	@Query("Select c from MappingDidCallFlowNumber c where didNumber=:didNumber")
	MappingDidCallFlowNumber findByDidNumber(String didNumber);

	void deleteAllByDidNumber(String didNumber);

	MappingDidCallFlowNumber findByDidNumberAndMappingNumber(String didNumber, String mappingNumber);
	
	Integer countByDidNumber(String didNumber);
}

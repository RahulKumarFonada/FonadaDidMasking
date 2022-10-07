package com.fonada.masking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.OffHourManagement;

@Repository
@Transactional
public interface OffHourRepository extends JpaRepository<OffHourManagement, Long> {

	OffHourManagement findOneById(Long id);

}
